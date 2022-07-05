package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Thijs van Blanken
 * Created on: 27-6-2022
 */
@Controller
public class PantryUserController {

    PantryUserService pantryUserService;

    AcornUserService acornUserService;

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
            List<AcornUser> acornUsers = new ArrayList<>();

            for (PantryUser pantryuser: pantryUsers) {
                acornUsers.add(pantryuser.getUser());
            }
            model.addAttribute("acornUser", acornUsers);
        }
        return "pantryMembers";
    }





}
