package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.viewmodel.ShoppingListProductVM;

import java.util.List;

public interface ShoppingListService {
    List<ShoppingListProductVM> generateShoppingListForPantry(Long pantryId);
}
