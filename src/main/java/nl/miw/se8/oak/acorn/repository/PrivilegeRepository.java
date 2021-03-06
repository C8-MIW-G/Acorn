package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);
}
