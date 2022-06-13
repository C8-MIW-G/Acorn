package nl.miw.se8.oak.acorn.model;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter
public class PantryProduct {

    @Id @GeneratedValue
    private Long id;
    private Long pantryId;
    private String productName;
    private LocalDate expirationDate;
    @ManyToOne
    private ProductDefinition productDefinition;

}
