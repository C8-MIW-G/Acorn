package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter @Setter
public class PantryProduct {

    @Id @GeneratedValue
    private Long id;
    private LocalDate expirationDate;
    @ManyToOne
    private ProductDefinition productDefinition;
    @ManyToOne
    private Pantry pantry;

}
