package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

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

    // THIS IS A TEST FOR THE TESTBRANCH

    @GetMapping("/pantry/{pantryId}")
    protected String pantryContents(@PathVariable("pantryId") Long pantryId, Model model) {
        List<PantryProduct> products = pantryProductService.findAllByPantryId(pantryId);
        model.addAttribute("products", products);
        return "pantryContents";
    }

    @GetMapping("/pantry/{pantryId}/delete")
    protected String deletePantry(@PathVariable("pantryId") Long pantryId) {
        pantryService.deleteById(pantryId);
        return "redirect:/pantrySelection";
    }

}
