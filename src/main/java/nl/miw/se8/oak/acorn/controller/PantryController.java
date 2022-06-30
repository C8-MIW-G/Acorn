package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.viewmodel.PantryEditDto;
import nl.miw.se8.oak.acorn.viewmodel.PantryMapper;
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

    public PantryController(PantryService pantryService, PantryProductService pantryProductService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
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
        PantryMapper mapper = new PantryMapper();
        Pantry pantry = new Pantry();
        model.addAttribute("pantryEditDto",mapper.pantrytoDto(pantry));
        return "pantryEdit.html";
    }

    @GetMapping("/pantry/{pantryId}/edit")              // DTO CHECK
    protected String editPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            PantryMapper mapper = new PantryMapper();
            model.addAttribute(mapper.pantrytoDto(pantry.get()));
        }
        return "pantryEdit.html";
    }

    @PostMapping("/pantry/edit")                    // Works using DTO's, but code did not need to be altered
    protected String renamePantry(@ModelAttribute("pantry") Pantry pantry, BindingResult result) {
        if (!result.hasErrors()) {
            pantryService.save(pantry);
        }
        return "redirect:/pantrySelection";
    }
}
