package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter @Setter
public class Pantry {

    public static final long DEFAULT_ID = -1;
    public static final String DEFAULT_NAME = "";

    @Id @GeneratedValue
    private Long id;
    // pantry name cannot be shorter than two characters
    @Size(min = 2,message = "Please enter a message of at least 2 characters")
    private String name;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryProduct> pantryProducts;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryUser> pantryUsers;

    public Pantry(String name) {
        this.id = DEFAULT_ID;
        this.name = name;
        pantryProducts = new ArrayList<>();
        pantryUsers = new ArrayList<>();
    }

    public Pantry() {
        this(DEFAULT_NAME);
    }
}
