package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Role;

import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 27-6-2022
 * OMSCHRIJVING
 */

public interface RoleService {
    Role save(Role role);
    Optional<Role> findByName(String name);
}
