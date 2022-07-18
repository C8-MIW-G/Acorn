package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

/**
 * Author: Team Oak
 * Last edited on: 11-07-2022
 */
@Entity @Getter @Setter
public class ProductDefinition implements Comparable<ProductDefinition>{

    public static final long DEFAULT_ID = -1L;
    private static final String DEFAULT_NAME = "";
    public static final int MIN_PRODUCT_DEFINITION_NAME_LENGTH = 1;
    public static final int MAX_PRODUCT_DEFINITION_NAME_LENGTH = 100;
    public static final String ERROR_NAME_TOO_SHORT = "The product name should be between 1 and 100 characters long.";

    @Id @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "productDefinition", cascade = ALL)
    private Set<PantryProduct> pantryProducts;
    @OneToMany(mappedBy = "productDefinition", cascade = ALL)
    private Set<ShoppingListProduct> shoppingListProducts;

    // Used for seeding
    public ProductDefinition(String name) {
        this.name = name;
    }

    public ProductDefinition() {
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
        this.pantryProducts = new HashSet<>();
    }

    public ProductDefinition(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Sort ProductDefinitions by first letter
    @Override
    public int compareTo(ProductDefinition other) {
        if (this.getName().getBytes(StandardCharsets.UTF_8)[0] > other.getName().getBytes(StandardCharsets.UTF_8)[0]) {
            return 1;
        } else if (this.getName().getBytes(StandardCharsets.UTF_8)[0] < other.getName().getBytes(StandardCharsets.UTF_8)[0]) {
            return -1;
        } else {
            return 0;
        }
    }

}
