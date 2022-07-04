package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.repository.PantryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
