package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.ShoppingListProduct;
import nl.miw.se8.oak.acorn.repository.ShoppingListProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Service
public class ShoppingListProductServiceImplementation implements ShoppingListProductService{

    private final ShoppingListProductRepository shoppingListProductRepository;

    public ShoppingListProductServiceImplementation(ShoppingListProductRepository shoppingListProductRepository) {
        this.shoppingListProductRepository = shoppingListProductRepository;
    }

    @Override
    public ShoppingListProduct save(ShoppingListProduct shoppingListProduct) {
        return shoppingListProductRepository.save(shoppingListProduct);
    }

    @Override
    public List<ShoppingListProduct> findByPantryId(Long pantryId) {
        return shoppingListProductRepository.findByPantryId(pantryId);
    }

}
