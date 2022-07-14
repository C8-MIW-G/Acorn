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
public class PantryProduct implements Comparable <PantryProduct>{

    public static final long DEFAULT_ID = -1L;
    public static final int MIN_AMOUNT = 1;
    public static final int MAX_AMOUNT = 10;

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

    @Override
    public int compareTo(PantryProduct pantryProduct) {
        // You have to consider pantry products that do not have an expiration date!
        if (getExpirationDate() == null) {
            return 1;
        } else if (pantryProduct.getExpirationDate() == null) {
            return -1;
        } else {
            return getExpirationDate().compareTo(pantryProduct.getExpirationDate());
        }
    }
}
