package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(AcornUser acornUser);
    List<AcornUser> findAll();
    Optional<AcornUser> findByUsername(String username);
}
