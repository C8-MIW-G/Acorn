package nl.miw.se8.oak.acorn.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @Author Wicher Vos, Thijs van Blanken
 *
 * Describes the relation between Users and Pantries.
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
        boolean isAdministrator;

        public PantryUser(AcornUser user, Pantry pantry, boolean isAdministrator) {
                this.id = DEFAULT_ID;
                this.user = user;
                this.pantry = pantry;
                this.isAdministrator = isAdministrator;
        }

        public PantryUser(AcornUser user, Pantry pantry) {
                this(user, pantry, false);
        }

        public PantryUser() {
            this(new AcornUser(), new Pantry());
        }

}
