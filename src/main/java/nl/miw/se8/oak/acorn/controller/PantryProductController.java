package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Sylvia Kazakou
 * Deal with contents of a pantry
 */

@Controller
public class PantryProductController {
    PantryProductService pantryProductService;

    public PantryProductController(PantryProductService pantryProductService) {
        this.pantryProductService = pantryProductService;
    }

    @GetMapping("/pantry/{pantryId}")
    protected String pantryContents(@PathVariable("pantryId") Long pantryId, Model model) {
        List<PantryProduct> pantryProducts = pantryProductService.findAllByPantryId(pantryId);
        model.addAttribute("pantryProducts", pantryProducts);
        return "pantryContents";
    }

    @GetMapping("/pantryProduct/{pantryProductId}/delete")
    protected String deletePantryProduct(@PathVariable("pantryProductId") Long pantryProductId) {
        Optional<PantryProduct> pantryProduct = pantryProductService.findById(pantryProductId);
        Long pantryId = null;
        if (pantryProduct.isPresent()) {
            pantryId = pantryProduct.get().getPantry().getId();
        }
        pantryProductService.deleteById(pantryProductId);

        if (pantryId != null) {
            return "redirect:/pantry/" + pantryId;
        } else {
            return "redirect:/pantrySelection";
        }
    }
}
