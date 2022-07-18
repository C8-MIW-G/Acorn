package nl.miw.se8.oak.acorn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 * Describes a shopping list for a single pantry.
 */
@Entity
public class PantryShoppingList {

    @Id @GeneratedValue
    private Long id;
    @OneToOne
    private Pantry pantry;

    public PantryShoppingList(Pantry pantry) {
        this.pantry = pantry;
    }

    public PantryShoppingList() {

    }
}
