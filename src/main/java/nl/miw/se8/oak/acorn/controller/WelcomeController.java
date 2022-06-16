package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import org.springframework.beans.factory.annotation.Autowired;
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
 *
 * Contains ALL controller functionality
 */
@Controller
public class WelcomeController {

    PantryService pantryService;
    PantryProductService pantryProductService;

    @Autowired
    public WelcomeController(PantryService pantryService, PantryProductService pantryProductService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
    }

    @GetMapping("/")
    protected String homepage() {
        return "home";
    }

    @GetMapping("/pantrySelection")
    protected String pantrySelection(Model model) {
        List<Pantry> pantries = pantryService.findAll();
        model.addAttribute("pantries", pantries);
        return "pantrySelection";
    }

    @GetMapping("/pantry/{pantryId}")
    protected String pantryContents(@PathVariable("pantryId") Long pantryId, Model model) {
        List<PantryProduct> products = pantryProductService.findAllByPantryId(pantryId);
        model.addAttribute("products", products);
        return "pantryContents";
    }

    @GetMapping("/pantry/{pantryId}/delete")
    protected String deletePantry(@PathVariable("pantryId") Long pantryId) {    // @path variabel etc is 1 variabele
        pantryService.deleteById(pantryId);     // delete by id is gewoon bestaande method
        return "redirect:/pantrySelection";
    }

    @GetMapping("/pantry/create")
    protected String createPantry(Model model) {
        model.addAttribute("pantry", new Pantry());
        return "pantryEdit.html";
    }

    @GetMapping("/pantry/{pantryId}/edit")
    protected String editPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        model.addAttribute("pantry", pantry);
        return "pantryEdit.html";
    }

    @PostMapping("/pantry/edit")
    protected String renamePantry(@ModelAttribute("pantry") Pantry pantry, BindingResult result) {
        if (!result.hasErrors()) {
            pantryService.save(pantry);
        }
        return "redirect:/pantrySelection";
    }

    @GetMapping("/productDefinitions")
    protected String productDefinitions() {
        return "productDefinitions";
    }

}
