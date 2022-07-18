package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryShoppingList;
import nl.miw.se8.oak.acorn.repository.PantryShoppingListRepository;
import org.springframework.stereotype.Service;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Service
public class PantryShoppingListServiceImplementation implements PantryShoppingListService{

    PantryShoppingListRepository pantryShoppingListRepository;

    public PantryShoppingListServiceImplementation(PantryShoppingListRepository pantryShoppingListRepository) {
        this.pantryShoppingListRepository = pantryShoppingListRepository;
    }

    @Override
    public PantryShoppingList save(PantryShoppingList pantryShoppingList) {
        return pantryShoppingListRepository.save(pantryShoppingList);
    }
}
