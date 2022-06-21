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

    public static final long DEFAULT_ID = -1L;

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private ProductDefinition productDefinition;
    @ManyToOne
    private Pantry pantry;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expirationDate;

    public PantryProduct(Pantry pantry, ProductDefinition productDefinition, LocalDate expirationDate) {
        this.id = DEFAULT_ID;
        this.pantry = pantry;
        this.productDefinition = productDefinition;
        this.expirationDate = expirationDate;
    }

    public PantryProduct() {
        this.id = DEFAULT_ID;
    }

}
