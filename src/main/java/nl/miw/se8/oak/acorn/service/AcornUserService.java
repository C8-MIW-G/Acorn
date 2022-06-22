package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface AcornUserService {
    Optional<AcornUser> findById(Long id);
    void save(AcornUser acornUser);
    List<AcornUser> findAll();
    Optional<AcornUser> findByUsername(String username);

    // Important for logging in
    UserDetails loadUserByUsername(String username);
}
