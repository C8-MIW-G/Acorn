package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryShoppingList;

import java.util.Optional;

public interface PantryShoppingListService {
    PantryShoppingList save(PantryShoppingList pantryShoppingList);
    Optional<PantryShoppingList> findByPantryId(Long pantryId);

}
