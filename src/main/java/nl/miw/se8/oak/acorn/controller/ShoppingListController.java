package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.ShoppingListProduct;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ShoppingListProductService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.ShoppingListProductVM;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    private final ShoppingListProductService shoppingListProductService;

    public ShoppingListController(PantryService pantryService,
                                  ShoppingListProductService shoppingListProductService) {
        this.pantryService = pantryService;
        this.shoppingListProductService = shoppingListProductService;
    }

    @GetMapping("/pantry/{pantryId}/shopping-list")
    protected String fetchShoppingList(@PathVariable("pantryId") Long pantryId,
                                       Model model) {
        // Look up pantry
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        // Fetch shopping list products
        List<ShoppingListProduct> shoppingListProducts = shoppingListProductService.findByPantryId(pantryId);
        List<ShoppingListProductVM> shoppingListProductVMS = new ArrayList<>();
        for (ShoppingListProduct product : shoppingListProducts) {
            shoppingListProductVMS.add(Mapper.shoppingListProductToVM(product));
        }

        model.addAttribute("products", shoppingListProductVMS);
        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        return "pantryShoppingList";
    }
}
