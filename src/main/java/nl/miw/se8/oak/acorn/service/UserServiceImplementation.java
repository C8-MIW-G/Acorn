package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{

    UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(AcornUser acornUser) {
        userRepository.save(acornUser);
    }

    @Override
    public List<AcornUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AcornUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
