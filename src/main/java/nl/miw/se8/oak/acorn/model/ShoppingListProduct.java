package nl.miw.se8.oak.acorn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 * Describes a product on a shopping list
 */
@Entity
public class ShoppingListProduct {

    @Id @GeneratedValue
    Long id;

    @ManyToOne
    private Pantry pantry;

    @ManyToOne
    private ProductDefinition productDefinition;

    public ShoppingListProduct(Pantry pantry, ProductDefinition productDefinition) {
        this.pantry = pantry;
        this.productDefinition = productDefinition;
    }

    public ShoppingListProduct() {

    }
}
