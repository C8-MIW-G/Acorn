package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.repository.PantryUserRepository;

public class PantryUserServiceImplementation implements PantryUserService{

    PantryUserRepository pantryUserRepository;

    public PantryUserServiceImplementation(PantryUserRepository pantryUserRepository) {
        this.pantryUserRepository = pantryUserRepository;
    }
}
