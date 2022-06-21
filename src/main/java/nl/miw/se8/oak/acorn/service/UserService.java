package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    List<User> findAll();
    Optional<User> findByUsername(String username);
}
