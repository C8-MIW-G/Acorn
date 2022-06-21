package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.AcornUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcornUserRepository extends JpaRepository<AcornUser, Long> {
    Optional<AcornUser> findByUsername(String username);
}
