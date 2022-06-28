package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Privilege;
import nl.miw.se8.oak.acorn.repository.PrivilegeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 27-6-2022
 * OMSCHRIJVING
 */

@Service
public class PrivilegeServiceImplementation implements PrivilegeService {

    PrivilegeRepository privilegeRepository;

    public PrivilegeServiceImplementation(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege save(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public Optional<Privilege> findByName(String name) {
        return privilegeRepository.findByName(name);
    }
}
