package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.controller.SecurityController;
import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.model.Role;
import nl.miw.se8.oak.acorn.repository.PantryUserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 4-7-2022
 */
@Service
public class AuthorizationServiceImplementation implements AuthorizationService {

    PantryUserRepository pantryUserRepository;
    RoleService roleService;

    public AuthorizationServiceImplementation(PantryUserRepository pantryUserRepository, RoleService roleService) {
        this.pantryUserRepository = pantryUserRepository;
        this.roleService = roleService;
    }

    public boolean userCanAccessPantry(Long pantryId) {
        if (currentUserIsAdmin() || currentUserIsMemberOfPantry(pantryId)) {
            return true;
        }
        return false;
    }

    private boolean currentUserIsAdmin() {
        Collection<Role> roles = SecurityController.getCurrentUser().getRoles();
        for (Role role : roles) {
            if (role.getName().equals(Role.ROLE_ADMIN)) {
                return true;
            }
        }
        return false;
    }

    private boolean currentUserIsMemberOfPantry(Long pantryId) {
        AcornUser user = SecurityController.getCurrentUser();
        List<PantryUser> pantryUsers = pantryUserRepository.findPantryUserByUser(user);
        for (PantryUser pantryUser : pantryUsers) {
            if (Objects.equals(pantryUser.getPantry().getId(), pantryId)) {
                return true;
            }
        }
        return false;
    }
}
