package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter @Setter
public class Pantry {

    public static final long DEFAULT_ID = -1;
    public static final int MIN_PANTRY_NAME_LENGTH = 2;
    public static final String ERROR_NAME_TOO_SHORT = "Please enter a message of at least 2 characters";
    public static final String DEFAULT_PANTRY_NAME = "";
    public static final String DEFAULT_NAME = DEFAULT_PANTRY_NAME;

    @Id @GeneratedValue
    private Long id;
    @Size(min = MIN_PANTRY_NAME_LENGTH, message = ERROR_NAME_TOO_SHORT)
    private String name;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryProduct> pantryProducts;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryUser> pantryUsers;

    public Pantry() {
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
    }
}
