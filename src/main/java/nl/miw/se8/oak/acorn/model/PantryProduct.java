package nl.miw.se8.oak.acorn.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

}
