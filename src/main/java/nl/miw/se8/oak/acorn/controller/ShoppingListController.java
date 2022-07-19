package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.RequiredProduct;
import nl.miw.se8.oak.acorn.service.AuthorizationService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.RequiredProductService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.RequiredProductVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Controller
public class ShoppingListController {

    private final PantryService pantryService;
    private final RequiredProductService requiredProductService;
    private final AuthorizationService authorizationService;

    public ShoppingListController(PantryService pantryService,
                                  RequiredProductService requiredProductService,
                                  AuthorizationService authorizationService) {
        this.pantryService = pantryService;
        this.requiredProductService = requiredProductService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/pantry/{pantryId}/shopping-list")
    protected String fetchShoppingList(@PathVariable("pantryId") Long pantryId,
                                       Model model) {
        return "redirect:/pantry/{pantryId}";
    }

    @GetMapping("/pantry/{pantryId}/stock-requirements")
    protected String getStockRequirements(@PathVariable("pantryId") Long pantryId,
                                          Model model) {
        // Check authorization
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Look up pantry
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        // Fetch shopping list products
        List<RequiredProduct> requiredProducts = requiredProductService.findByPantryId(pantryId);
        List<RequiredProductVM> requiredProductVMS = new ArrayList<>();
        for (RequiredProduct product : requiredProducts) {
            requiredProductVMS.add(Mapper.requiredProductToVM(product));
        }

        // Load new page with model
        model.addAttribute("products", requiredProductVMS);
        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        return "requirementsPantryStock";
    }

    @GetMapping("/pantry/{pantryId}/stock-requirements/add")
    protected String addStockRequirements(@PathVariable("pantryId") Long pantryId,
                                          Model model) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        return "requirementsPantryStockAdd";
    }

}
