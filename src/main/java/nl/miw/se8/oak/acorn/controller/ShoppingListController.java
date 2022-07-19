package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.service.AuthorizationService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ShoppingListService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Author: Thijs van Blanken
 * Created on: 19-7-2022
 */

@Controller
public class ShoppingListController {

    private final AuthorizationService authorizationService;
    private final PantryService pantryService;
    private final ShoppingListService shoppingListService;

    public ShoppingListController(AuthorizationService authorizationService,
                                  PantryService pantryService,
                                  ShoppingListService shoppingListService) {
        this.authorizationService = authorizationService;
        this.pantryService = pantryService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/pantry/{pantryId}/shopping-list")
    protected String fetchShoppingList(@PathVariable("pantryId") Long pantryId,
                                       Model model) {
        // Check authorization
        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Look up pantry
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        // Fetch shopping list
        // TODO

        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        model.addAttribute("shoppingList", new ArrayList<>());
        return "ShoppingList";
    }

}
