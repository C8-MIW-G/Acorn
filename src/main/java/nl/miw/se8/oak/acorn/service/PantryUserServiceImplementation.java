package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.controller.SecurityController;
import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.repository.PantryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PantryUserServiceImplementation implements PantryUserService{

    PantryUserRepository pantryUserRepository;

    public PantryUserServiceImplementation(PantryUserRepository pantryUserRepository) {
        this.pantryUserRepository = pantryUserRepository;
    }

    @Override
    public List<PantryUser> findAll() {
        return pantryUserRepository.findAll();
    }

    @Override
    public void save(PantryUser pantryUser) {
        pantryUserRepository.save(pantryUser);
    }

    @Override
    public List<PantryUser> findPantryUserByUser(AcornUser user) {
        return pantryUserRepository.findPantryUserByUser(user);
    }

    public boolean currentUserHasAccessToPantry(Long pantryId) {
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
