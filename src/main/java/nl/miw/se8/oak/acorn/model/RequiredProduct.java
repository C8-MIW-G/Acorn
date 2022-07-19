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
        this.amount = 1;
    }

    public RequiredProduct() {

    }
}
