package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryShoppingList;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryShoppingListService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Controller
public class PantryShoppingListController {

    private final PantryShoppingListService pantryShoppingListService;
    private final PantryService pantryService;

    public PantryShoppingListController(PantryShoppingListService pantryShoppingListService,
                                        PantryService pantryService) {
        this.pantryShoppingListService = pantryShoppingListService;
        this.pantryService = pantryService;
    }

    @GetMapping("/pantry/{pantryId}/shopping-list")
    protected String fetchShoppingList(@PathVariable("pantryId") Long pantryId,
                                       Model model) {
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        Optional<PantryShoppingList> shoppingList = pantryShoppingListService.findByPantryId(pantryId);

        if (pantry.isEmpty() || shoppingList.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        model.addAttribute("shoppingList", shoppingList.get());
        return "pantryShoppingList";
    }
}
