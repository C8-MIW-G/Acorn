package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.AuthorizationService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.PantryMemberVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
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
        Optional<Pantry> pantry = pantryService.findById(pantryId);

        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (pantry.isPresent()) {
            List<PantryUser> pantryUsers = pantryUserService.findPantryUserByPantry(pantry.get());
            List<PantryMemberVM> pantryMemberVMS = new ArrayList<>();

            for (PantryUser pantryUser : pantryUsers) {
                pantryMemberVMS.add(Mapper.pantryUserToPantryMemberVM(pantryUser));
            }
            model.addAttribute("pantryMembers", pantryMemberVMS);
            model.addAttribute("currentUserId", SecurityController.getCurrentUser().getId());
        }
        return "pantryMembers";
    }

    @GetMapping("/pantry/{pantryId}/members/addMember")
    protected String addPantryMember(@PathVariable("pantryId") Long pantryId) {

        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "pantryMembersAdd";
    }

    @PostMapping("/pantry/{pantryId}/members/addMember")
    protected String createPantryUser(@PathVariable("pantryId") Long pantryId,
                                      @ModelAttribute("userEmail") String userEmail,
                                      RedirectAttributes redirectAttributes) {

        Optional<AcornUser> acornUser = acornUserService.findByEmail(userEmail);
        if (acornUser.isPresent()) {
            if (!pantryUserService.userIsInPantry(acornUser.get().getId(), pantryId)) {
                Pantry pantry = pantryService.findById(pantryId).get();
                PantryUser pantryUser = new PantryUser(acornUser.get(), pantry);
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
                                      @PathVariable("pantryId") Long pantryId) {
        Long userId = SecurityController.getCurrentUser().getId();
        Optional<PantryUser> currentUser = pantryUserService.findPantryUserByUserIdAndPantryId(userId, pantryId);
        if(currentUser.isPresent() && currentUser.get().isAdministrator()) {
            if(userId != pantryUserId) {
                pantryUserService.deleteById(pantryUserId);
            }
        }
        return "redirect:/pantry/{pantryId}/members";
    }

    @GetMapping("/pantry/{pantryId}/leave")
    protected String leavePantry(@PathVariable("pantryId") Long pantryId,
                                 RedirectAttributes redirectAttributes) {
        Long userId = SecurityController.getCurrentUser().getId();
        Optional<PantryUser> pantryUser = pantryUserService.findPantryUserByUserIdAndPantryId(userId, pantryId);
        if (pantryUser.isPresent()) {
            // Cannot leave a pantry where you are the only member.
            if (!pantryUserService.pantryHasMoreThanOneMember(pantryId)) {
                redirectAttributes.addFlashAttribute("errorMessage", "You cannot leave a pantry if you are the only member. You can delete the pantry instead.");
                return "redirect:/pantry/" + pantryId;
            } else if (pantryUserService.userIsTheOnlyPantryAdmin(userId, pantryId)) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "You cannot leave a pantry if you are the only pantry administrator.\n");
                return "redirect:/pantry/" + pantryId;
            }
            pantryUserService.deleteById(pantryUser.get().getId());
        }

        return "redirect:/pantrySelection";
    }
}
