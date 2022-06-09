package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.repository.PantryProductRepository;
import nl.miw.se8.oak.acorn.repository.PantryRepository;
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

    PantryRepository pantryRepository;
    PantryProductRepository pantryProductRepository;

    public WelcomeController(PantryRepository pantryRepository, PantryProductRepository pantryProductRepository) {
        this.pantryRepository = pantryRepository;
        this.pantryProductRepository = pantryProductRepository;
    }

    @GetMapping("/")
    protected String homepage() {
        return "home";
    }

    // TODO /error mapping

    @GetMapping("/pantrySelection")
    protected String pantrySelection(Model model) {
        List<Pantry> pantries = pantryRepository.findAll();
        if (pantries.size() > 0) {
            model.addAttribute("pantries", pantries);
            return "pantrySelection";
        }
        // TODO "No pantries found" message (else statement)
        return "redirect:/";
    }

    @GetMapping("/pantry/{pantryId}")
    protected String pantryContents(@PathVariable("pantryId") Long pantryId, Model model) {

        List<PantryProduct> products = pantryProductRepository.findAllByPantryId(pantryId);
        // TODO improve check
        if (products != null) {
            model.addAttribute("products", products);
            return "pantryContents";
        }
        return "redirect:/";
    }

}
