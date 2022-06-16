package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
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
    protected String pantryContents(@PathVariable("pantryId") Long pantryId,Model model) {
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
        return "redirect:/pantry/" + pantryId;
    }

    @GetMapping("/pantry/{pantryId}/add")
    protected String addNewItemInPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        PantryProduct pantryProduct = new PantryProduct();

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()){
            pantryProduct.setPantry(pantry.get());
        }
        model.addAttribute("pantryProduct", pantryProduct);
        return "addEditPantryProduct";
    }

    @PostMapping("/pantry/{pantryId}/add")
    protected String savePantryProduct(@PathVariable ("pantryId") Long pantryId, @ModelAttribute("pantryProduct") PantryProduct pantryProduct, BindingResult result) {
        if (!result.hasErrors()) {

            pantryProduct.setExpirationDate(LocalDate.now());

            Optional<ProductDefinition> productDefinition = productDefinitionService.findById(-2L);
            if (productDefinition.isPresent()) {
                pantryProduct.setProductDefinition(productDefinition.get());
            }

            pantryProductService.save(pantryProduct);
        }
        return "redirect:/pantry/{pantryId}";
    }
}

