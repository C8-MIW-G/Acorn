package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 13-6-2022
 * OMSCHRIJVING
 */
@Entity @Getter @Setter
public class ProductDefinition {

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
}
