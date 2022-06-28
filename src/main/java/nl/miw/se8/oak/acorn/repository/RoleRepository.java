package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 27-6-2022
 * OMSCHRIJVING
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
