package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.repository.PantryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<PantryUser> findPantryUserByPantry(Pantry pantry) {
        return pantryUserRepository.findPantryUserByPantry(pantry);
    }

    @Override
    public Optional<PantryUser> findById(Long pantryUserId) {
        return pantryUserRepository.findById(pantryUserId);
    }

    @Override
    public void deleteById(Long id) {
        pantryUserRepository.deleteById(id);
    }

}
