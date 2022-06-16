package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter
public class Pantry {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryProduct> pantryProducts;

    public Pantry() {
        this.id = -9l;
    }
}
