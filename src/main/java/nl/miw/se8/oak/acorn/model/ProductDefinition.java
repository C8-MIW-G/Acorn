package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 13-6-2022
 * OMSCHRIJVING
 */
@Entity @Getter @Setter
public class ProductDefinition {

    @Id @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "productDefinition", cascade = ALL)
    private List<PantryProduct> pantryProducts;

    public ProductDefinition() {

    }

    public ProductDefinition(Long id, String name, List<PantryProduct> pantryProducts) {
        this.id = id;
        this.name = name;
        this.pantryProducts = pantryProducts;
    }
}
