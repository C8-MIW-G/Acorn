package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 13-6-2022
 */
@Entity @Getter @Setter
public class ProductDefinition implements Comparable<ProductDefinition>{

    private static final String DEFAULT_NAME = "New Product";

    @Id @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "productDefinition")
    private List<PantryProduct> pantryProducts;

    public ProductDefinition() {
        this.name = DEFAULT_NAME;
        this.pantryProducts = new ArrayList<>();
        // TODO should this be a set?
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
