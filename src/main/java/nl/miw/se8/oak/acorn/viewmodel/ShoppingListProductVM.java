package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Thijs van Blanken
 * Created on: 19-7-2022
 */
@Getter @Setter
public class ShoppingListProductVM {

    private String name;
    private int amount;

    public ShoppingListProductVM(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public ShoppingListProductVM() {
    }
}
