package nl.miw.se8.oak.acorn.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @Author Wicher Vos Thijs van Blanken
 *
 *
 */

@Entity @Getter @Setter
public class PantryUser {


        public static final long DEFAULT_ID = -1L;

        @Id @GeneratedValue
        private Long id;
        @ManyToOne
        private AcornUser user;
        @ManyToOne
        private Pantry pantry;

        public PantryUser(AcornUser user, Pantry pantry) {
                this.id = DEFAULT_ID;
                this.user = user;
                this.pantry = pantry;
        }

        public PantryUser() {
            this(new AcornUser(), new Pantry());
        }

}
