package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.model.RequiredProduct;
import nl.miw.se8.oak.acorn.viewmodel.ShoppingListProductVM;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Author: Thijs van Blanken
 * Created on: 19-7-2022
 */
@Service
public class ShoppingListServiceImplementation implements ShoppingListService{

    PantryProductService pantryProductService;
    RequiredProductService requiredProductService;

    public ShoppingListServiceImplementation(PantryProductService pantryProductService, RequiredProductService requiredProductService) {
        this.pantryProductService = pantryProductService;
        this.requiredProductService = requiredProductService;
    }

    @Override
    public List<ShoppingListProductVM> generateShoppingListForPantry(Long pantryId) {
        // Fetch all pantry products
        List<PantryProduct> pantryProducts = pantryProductService.findAllByPantryId(pantryId);

        // Fetch all required products
        List<RequiredProduct> requiredProducts = requiredProductService.findByPantryId(pantryId);


        // Compare the two lists and generate a new (shopping) list based on the difference between the two
        List<ShoppingListProductVM> shoppingList = new ArrayList<>();

        for (RequiredProduct requiredProduct : requiredProducts) {
            int amountInPantry = (int) pantryProducts.stream().filter(pantryProduct ->
                    pantryProduct.getProductDefinition().getId().equals(requiredProduct.getProductDefinition().getId())).count();
            int amountToGet = requiredProduct.getAmount() - amountInPantry;
            if (amountToGet > 0) {
                shoppingList.add(new ShoppingListProductVM(requiredProduct.getProductDefinition().getName(), amountToGet));
            }
        }

        return shoppingList;
    }


}
