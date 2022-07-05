package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryUserRepository extends JpaRepository<PantryUser, Long> {
    List<PantryUser> findPantryUserByUser(AcornUser user);

    List<PantryUser> findPantryUserByPantry(Pantry pantry);
}
