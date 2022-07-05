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
 * Author: Thijs van Blanken
 * Created on: 13-6-2022
 */
@Entity @Getter @Setter
public class ProductDefinition implements Comparable<ProductDefinition>{

    public static final long DEFAULT_ID = -1L;
    private static final String DEFAULT_NAME = "";

    @Id @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "productDefinition", cascade = ALL)
    private Set<PantryProduct> pantryProducts;

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
