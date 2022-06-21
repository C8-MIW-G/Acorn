package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.repository.PantryUserRepository;
import org.springframework.stereotype.Service;

@Service
public class PantryUserServiceImplementation implements PantryUserService{

    PantryUserRepository pantryUserRepository;

    public PantryUserServiceImplementation(PantryUserRepository pantryUserRepository) {
        this.pantryUserRepository = pantryUserRepository;
    }

    @Override
    public void save(PantryUser pantryUser) {
        pantryUserRepository.save(pantryUser);
    }
}
