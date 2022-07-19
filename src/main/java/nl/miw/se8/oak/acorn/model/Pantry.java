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
@Entity @Getter @Setter
public class Pantry {

    public static final long DEFAULT_ID = -1;
    public static final int MIN_PANTRY_NAME_LENGTH = 4;
    public static final int MAX_PANTRY_NAME_LENGTH = 40;
    public static final String ERROR_NAME_TOO_SHORT = "The pantry name should be between 4 and 40 characters long.";
    public static final String DEFAULT_PANTRY_NAME = "";
    public static final String DEFAULT_NAME = DEFAULT_PANTRY_NAME;

    @Id @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryProduct> pantryProducts;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<PantryUser> pantryUsers;
    @OneToMany(mappedBy = "pantry",  cascade = ALL)
    private List<RequiredProduct> requiredProducts;

    public Pantry() {
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
    }
}
