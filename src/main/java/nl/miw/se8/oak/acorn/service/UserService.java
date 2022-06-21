package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> findAll();
}
