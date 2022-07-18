package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.AuthorizationService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import nl.miw.se8.oak.acorn.viewmodel.MakePantryAdminVM;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.PantryMemberVM;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PantryUserController {

    AcornUserService acornUserService;
    PantryUserService pantryUserService;
    AuthorizationService authorizationService;
    PantryService pantryService;

    public PantryUserController(AcornUserService acornUserService,
                                PantryUserService pantryUserService,
                                PantryService pantryService,
                                AuthorizationService authorizationService) {
        this.acornUserService = acornUserService;
        this.pantryUserService = pantryUserService;
        this.pantryService = pantryService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/pantry/{pantryId}/members")
    protected String pantryMembers(@PathVariable("pantryId") Long pantryId, Model model) {
        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            List<PantryUser> pantryUsers = pantryUserService.findPantryUserByPantry(pantry.get());
            List<PantryMemberVM> pantryMemberVMS = new ArrayList<>();
            for (PantryUser pantryUser : pantryUsers) {
                pantryMemberVMS.add(Mapper.pantryUserToPantryMemberVM(pantryUser));
            }
            model.addAttribute("pantryMembers", pantryMemberVMS);
            model.addAttribute("currentUserIsAdmin", authorizationService.userCanEditPantry(pantryId));
            model.addAttribute("currentUserId", SecurityController.getCurrentUser().getId());
        }
        return "pantryMembers";
    }

    @GetMapping("/pantry/{pantryId}/members/addMember")
    protected String addPantryMember(@PathVariable("pantryId") Long pantryId) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "pantryMembersAdd";
    }

    @PostMapping("/pantry/{pantryId}/members/addMember")
    protected String createPantryUser(@PathVariable("pantryId") Long pantryId,
                                      @ModelAttribute("userEmail") String userEmail,
                                      RedirectAttributes redirectAttributes) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<AcornUser> acornUser = acornUserService.findByEmail(userEmail);
        if (acornUser.isPresent()) {
            // Cannot add the same user twice to a pantry
            if (!pantryUserService.userIsInPantry(acornUser.get().getId(), pantryId)) {
                Pantry pantry = pantryService.findById(pantryId).get();
                PantryUser pantryUser = new PantryUser(acornUser.get(), pantry, false);
                pantryUserService.save(pantryUser);
                redirectAttributes.addFlashAttribute("errorMessage", "Successfully added " + acornUser.get().getName() + " to your pantry");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", acornUser.get().getName() + " is already a member of this pantry!");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not add that user.");
        }

        return "redirect:/pantry/" + pantryId + "/members";
    }

    @GetMapping("/pantry/{pantryId}/members/{pantryUserId}/delete")
    protected String deletePantryUser(@PathVariable("pantryUserId") Long pantryUserId,
                                      @PathVariable("pantryId") Long pantryId,
                                      RedirectAttributes redirectAttributes) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Long currentUserId = SecurityController.getCurrentUser().getId();
        if(!Objects.equals(currentUserId, pantryUserId)) { // You cannot remove yourself from the list
            Optional<PantryUser> pantryUser = pantryUserService.findById(pantryUserId);
            if (pantryUser.isPresent()) {
                String removedUserName = acornUserService.findById(pantryUser.get().getUser().getId()).get().getName();
                if (!pantryUserService.userIsTheOnlyPantryAdmin(pantryUser.get().getUser().getId(), pantryId)) {
                    pantryUserService.deleteById(pantryUserId);
                    redirectAttributes.addFlashAttribute("errorMessage", "Successfully removed " + removedUserName + " from your pantry.");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Cannot remove " + removedUserName + " because they are the only pantry administrator.");
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Could not remove that person.");
            }
        }
        return "redirect:/pantry/{pantryId}/members";
    }

    @GetMapping("/pantry/{pantryId}/leave")
    protected String leavePantry(@PathVariable("pantryId") Long pantryId,
                                 RedirectAttributes redirectAttributes) {
        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Long userId = SecurityController.getCurrentUser().getId();
        Optional<PantryUser> pantryUser = pantryUserService.findPantryUserByUserIdAndPantryId(userId, pantryId);
        if (pantryUser.isPresent()) {
            // Cannot leave a pantry where you are the only member.
            if (!pantryUserService.pantryHasMoreThanOneMember(pantryId)) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "You cannot leave a pantry if you are the only member. You can delete the pantry instead.");
                return "redirect:/pantry/" + pantryId;
            // Cannot leave a pantry where you are the only pantry administrator.
            } else if (authorizationService.currentUserIsAdminOfPantry(pantryId) && pantryUserService.userIsTheOnlyPantryAdmin(userId, pantryId)) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "You cannot leave a pantry if you are the only pantry administrator.\n");
                return "redirect:/pantry/" + pantryId;
            }
            pantryUserService.deleteById(pantryUser.get().getId());
        }
        return "redirect:/pantrySelection";
    }

    @GetMapping("/pantry/{pantryId}/members/{pantryUserId}/makeAdmin")
    protected String makeAdmin(@PathVariable("pantryId") Long pantryId,
                               @PathVariable("pantryUserId") Long pantryUserId,
                               RedirectAttributes redirectAttributes) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<PantryUser> pantryUser = pantryUserService.findById(pantryUserId);

        if (pantryUser.isPresent()) {
            PantryUser newAdmin = pantryUser.get();
            newAdmin.setAdministrator(true);
            pantryUserService.save(newAdmin);
            redirectAttributes.addFlashAttribute("errorMessage",
                    pantryUser.get().getUser().getName()+ " is now an admin");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "This user could not be made an admin");
        }

        return "redirect:/pantry/{pantryId}/members";
    }

    @GetMapping("/pantry/{pantryId}/members/{pantryUserId}/pantryUserProfile")
    protected String pantryMemberProfile( @PathVariable("pantryId") Long pantryId, @PathVariable("pantryUserId") Long pantryUserId, Model model) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Optional<PantryUser> pantryMember = pantryUserService.findById(pantryUserId);
        if (pantryMember.isPresent()) {
            MakePantryAdminVM makePantryAdminVM = Mapper.pantryUsertoMakePantryAdminVM(pantryMember.get());
            model.addAttribute("pantryMember", makePantryAdminVM);
        }
        model.addAttribute("currentUserIsAdmin", authorizationService.userCanEditPantry(pantryId));
        model.addAttribute("currentUserId", SecurityController.getCurrentUser().getId());
        return "pantryMemberProfile";
    }

    @GetMapping("/pantry/{pantryId}/members/{pantryUserId}/unmakeAdmin/")
    protected String unmakeAdmin(@PathVariable("pantryId") Long pantryId, PantryUser pantryUser){                                          //@PathVariable ("pantryUserId") Long pantryUserId) {
        if (authorizationService.currentUserIsAdminOfPantry(pantryId) == true && pantryUser.getUser().getId() == SecurityController.getCurrentUser().getId());



        return "pantryMemberProfile";
    }


}