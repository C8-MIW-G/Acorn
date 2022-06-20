package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.security.cert.PolicyNode;
import java.time.LocalDate;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Entity @Getter @Setter
public class PantryProduct {

    @Id @GeneratedValue
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expirationDate;
    @ManyToOne
    private ProductDefinition productDefinition;
    @ManyToOne
    private Pantry pantry;

    public PantryProduct() {
        this.id = -1L;
    }

}
