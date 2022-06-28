package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Privilege;

import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 27-6-2022
 * OMSCHRIJVING
 */

public interface PrivilegeService {
    Privilege save(Privilege privilege);
    Optional<Privilege> findByName(String name);

}
