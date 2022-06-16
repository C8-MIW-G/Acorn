package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
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
    ProductDefinitionService productDefinitionService;

    @Autowired
    public WelcomeController(
            PantryService pantryService,
            PantryProductService pantryProductService,
            ProductDefinitionService productDefinitionService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
        this.productDefinitionService = productDefinitionService;
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

    @GetMapping("/pantry/{pantryId}/delete")
    protected String deletePantry(@PathVariable("pantryId") Long pantryId) {
        pantryService.deleteById(pantryId);
        return "redirect:/pantrySelection";
    }
}
