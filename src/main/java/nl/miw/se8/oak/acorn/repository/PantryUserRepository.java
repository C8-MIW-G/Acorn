package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PantryUserRepository extends JpaRepository<PantryUser, Long> {
    List<PantryUser> findPantryUserByUser(AcornUser user);
    List<PantryUser> findPantryUserByPantry(Pantry pantry);
    Optional<PantryUser> findPantryUserByUserIdAndPantryId(Long userId, Long pantryId);
}
