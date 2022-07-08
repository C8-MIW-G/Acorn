package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.AuthorizationService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.PantryMemberVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PantryUserController {

    PantryUserService pantryUserService;

    AcornUserService acornUserService;
    AuthorizationService authorizationService;

    PantryService pantryService;

    public PantryUserController(PantryUserService pantryUserService, PantryService pantryService,
                                AuthorizationService authorizationService) {
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
    protected String createPantryUser(@PathVariable("pantryId") Long pantryId, String email) {


//        make sure user is pantry administrator
//        retrieve acornuser using email
//        create new pantryuser using pantry id and email
//                post new pantryuser


        return null;
    }

    @GetMapping("/pantry/{pantryId}/members/{pantryUserId}/delete")
    protected String deletePantryUser(@PathVariable("pantryUserId") Long pantryUserId,
                                      @PathVariable("pantryId") Long pantryId) {
        pantryUserService.deleteById(pantryUserId);
        return "redirect:/pantry/{pantryId}/members";
    }

    @GetMapping("/pantry/{pantryId}/leave")
    protected String leavePantry(@PathVariable("pantryId") Long pantryId) {
        Long userId = SecurityController.getCurrentUser().getId();
        Optional<PantryUser> pantryUser = pantryUserService.findPantryUserByUserIdAndPantryId(userId, pantryId);
        if (pantryUser.isPresent()) {
            pantryUserService.deleteById(pantryUser.get().getId());
        }
        return "redirect:/pantrySelection";
    }
}
