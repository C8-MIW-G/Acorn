package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AcornUserRepository extends JpaRepository<AcornUser, Long> {
    Optional<AcornUser> findByEmail(String email);
    List<AcornUser> findByRolesContains(Role role);
}
