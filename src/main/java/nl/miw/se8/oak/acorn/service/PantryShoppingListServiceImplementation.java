package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryShoppingList;
import nl.miw.se8.oak.acorn.repository.PantryShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<PantryShoppingList> findByPantryId(Long pantryId) {
        return pantryShoppingListRepository.findByPantryId(pantryId);
    }

    @Override
    public List<PantryShoppingList> findAll() {
        return pantryShoppingListRepository.findAll();
    }
}
