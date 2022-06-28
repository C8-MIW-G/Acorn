package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Role;
import nl.miw.se8.oak.acorn.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 27-6-2022
 * OMSCHRIJVING
 */
@Service
public class RoleServiceImplementation implements RoleService{

    RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
