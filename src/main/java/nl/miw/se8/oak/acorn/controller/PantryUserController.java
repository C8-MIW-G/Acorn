package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.PantryMemberVM;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PantryUserController {

    PantryUserService pantryUserService;
    PantryService pantryService;

    public PantryUserController(PantryUserService pantryUserService, PantryService pantryService) {
        this.pantryUserService = pantryUserService;
        this.pantryService = pantryService;
    }

    @GetMapping("/pantry/{pantryId}/members")
    protected String pantryMembers(@PathVariable("pantryId") Long pantryId, Model model) {
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            List<PantryUser> pantryUsers = pantryUserService.findPantryUserByPantry(pantry.get());
            List<PantryMemberVM> pantryMemberVMS = new ArrayList<>();
            for (PantryUser pantryUser: pantryUsers) {
                pantryMemberVMS.add(Mapper.pantryUserToPantryMemberVM(pantryUser));
            }
            model.addAttribute("pantryMembers", pantryMemberVMS);
        }
        return "pantryMembers";
    }

    @GetMapping("/pantry/{pantryId}/members/{pantryUserId}/delete")
    protected String deletePantryUser(@PathVariable("pantryUserId") Long pantryUserId,
                                      @PathVariable("pantryId") Long pantryId) {
        pantryUserService.deleteById(pantryUserId);
        return "redirect:/pantry/{pantryId}/members";
    }

}
