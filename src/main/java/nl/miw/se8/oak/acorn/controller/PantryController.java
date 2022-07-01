package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Controller
public class PantryController {

    PantryService pantryService;
    PantryProductService pantryProductService;
    PantryUserService pantryUserService;

    public PantryController(PantryService pantryService,
                            PantryProductService pantryProductService,
                            PantryUserService pantryUserService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
        this.pantryUserService = pantryUserService;
    }

    @GetMapping("/pantrySelection")                         // DTO need only names and id's I guess. does this need a DTO?
    protected String pantrySelection(Model model) {
        List<Pantry> pantries = pantryService.findAll();        // maak deze lijst en dan een -for each-loop voor elke pantry
        model.addAttribute("pantries", pantries);
        return "pantrySelection";
    }

    @GetMapping("/pantry/{pantryId}/delete")                // Does not need DTO
    protected String deletePantry(@PathVariable("pantryId") Long pantryId) {
        pantryService.deleteById(pantryId);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/pantry/create")                       // DTO CHECK
    protected String createPantry(Model model) {
        Mapper mapper = new Mapper();
        Pantry pantry = new Pantry();
        model.addAttribute("pantryToPantryEditViewmodel",mapper.pantryToPantryEditViewmodel(pantry));
        return "pantryEdit.html";
    }

    @GetMapping("/pantry/{pantryId}/edit")              // DTO CHECK
    protected String editPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            Mapper mapper = new Mapper();
            model.addAttribute("pantryToPantryEditViewmodel", mapper.pantryToPantryEditViewmodel(pantry.get()));
        }
        return "pantryEdit.html";
    }

    @PostMapping("/pantry/edit")
    protected String renamePantry(@ModelAttribute("pantry") Pantry pantry,
                                  BindingResult result,
                                  @AuthenticationPrincipal AcornUser acornUser) {
        // Check if a new pantry is created, or an existing pantry is edited.
        boolean newPantry = pantry.getId() == -1;

        if (!result.hasErrors()) {
            Pantry savedPantry = pantryService.save(pantry);

            // Sets current user as pantry administrator of newly created pantry
            if (newPantry) {
                PantryUser pantryUser = new PantryUser(acornUser, savedPantry, true);
                pantryUserService.save(pantryUser);
            }
        }
        return "redirect:/pantrySelection";
    }

}
