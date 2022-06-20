package nl.miw.se8.oak.acorn.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @Author Wicher Vos Thijs van Blanken
 *
 *
 */

@Entity @Getter @Setter
public class PantryUser {


        @Id @GeneratedValue
        private Long id;

        @ManyToOne
        private User user;

        @ManyToOne
        private Pantry pantry;

        public PantryUser() {
            this.id = -1L;
        }

}
