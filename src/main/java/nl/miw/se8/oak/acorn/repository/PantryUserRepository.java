package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.PantryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryUserRepository extends JpaRepository<PantryUser, Long> {
}
