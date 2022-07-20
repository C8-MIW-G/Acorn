package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 * Describes a product on a shopping list
 */
@Entity @Getter @Setter
public class RequiredProduct {

    public static final long DEFAULT_ID = -1L;
    public static final int DEFAULT_AMOUNT = 1;
    public static final int MAX_AMOUNT = 100;

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Pantry pantry;

    @ManyToOne
    private ProductDefinition productDefinition;

    private int amount;

    public RequiredProduct(Pantry pantry, ProductDefinition productDefinition) {
        this.pantry = pantry;
        this.productDefinition = productDefinition;
        this.amount = DEFAULT_AMOUNT;
    }

    public RequiredProduct() {
        this.id = DEFAULT_ID;
        this.amount = DEFAULT_AMOUNT;
    }

    public void addToAmount(int amount) {
        this.amount += amount;
    }
}
