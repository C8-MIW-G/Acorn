package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Sylvia Kazakou
 * Deal with contents of a pantry
 */
@Controller
public class PantryProductController {
    PantryProductService pantryProductService;
    ProductDefinitionService productDefinitionService;
    PantryService pantryService;

    public PantryProductController(PantryProductService pantryProductService, ProductDefinitionService productDefinitionService, PantryService pantryService) {
        this.pantryProductService = pantryProductService;
        this.productDefinitionService = productDefinitionService;
        this.pantryService = pantryService;
    }

    @GetMapping("/pantry/{pantryId}")
    protected String pantryContents(@PathVariable("pantryId") Long pantryId, Model model) {
        List<PantryProduct> pantryProducts = pantryProductService.findAllByPantryId(pantryId);
        model.addAttribute("pantryProducts", pantryProducts);

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            model.addAttribute("pantryName", pantry.get().getName());
            return "pantryContents";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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

    @GetMapping("/pantry/{pantryId}/add")
    protected String addNewItemInPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        PantryProduct pantryProduct = new PantryProduct();
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()){
            pantryProduct.setPantry(pantry.get());
        }
        model.addAttribute("pantryProduct", pantryProduct);

        List<ProductDefinition> products = productDefinitionService.findAll();
        model.addAttribute("productDefinitions", products);

        return "addEditPantryProduct";
    }

    @PostMapping("/pantry/{pantryId}/add")
    protected String savePantryProduct(
            @ModelAttribute("pantryProduct") PantryProduct pantryProduct,
            BindingResult result) {
        if (!result.hasErrors()) {
            if (pantryProduct.getProductDefinition() != null) {
                pantryProductService.save(pantryProduct);
            }
        }
        return "redirect:/pantry/" + pantryProduct.getPantry().getId();
    }

    @GetMapping("/pantry/{pantryId}/edit/{pantryProductId}")
    protected String editItemInPantry(@PathVariable("pantryId") Long pantryId,
                                      @PathVariable("pantryProductId") Long pantryProductId,
                                      Model model) {
        Optional<PantryProduct> pantryProduct = pantryProductService.findById(pantryProductId);
        if (pantryProduct.isPresent()) {
            model.addAttribute("pantryProduct", pantryProduct.get());
            return "addEditPantryProduct";
        }
        return "redirect:/pantry/" + pantryId;
    }

}

