package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.repository.UserRepository;

public class UserServiceImplementation implements UserService{

    UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
