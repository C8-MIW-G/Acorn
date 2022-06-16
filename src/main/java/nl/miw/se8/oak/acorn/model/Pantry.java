package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter @Setter
public class Pantry {

    public static final long DEFAULT_ID = -1L;
    public static final String DEFAULT_NAME = "New pantry";

    @Id @GeneratedValue
    private Long id;
    private String name;

    public Pantry() {
        this.id = DEFAULT_ID;
        this.name= DEFAULT_NAME;
    }
}
