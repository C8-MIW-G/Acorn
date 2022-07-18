package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 * Describes a shopping list for a single pantry.
 */
@Entity @Getter @Setter
public class PantryShoppingList {

    @Id @GeneratedValue
    private Long id;
    @OneToOne
    private Pantry pantry;
    @OneToMany(mappedBy = "pantryShoppingList",  cascade = ALL)
    private List<ShoppingListProduct> products;

    public PantryShoppingList(Pantry pantry) {
        this.pantry = pantry;
    }

    public PantryShoppingList() {

    }
}
