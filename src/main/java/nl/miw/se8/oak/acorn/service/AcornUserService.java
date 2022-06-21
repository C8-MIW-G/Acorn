package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AcornUserService {
    void save(AcornUser acornUser);
    List<AcornUser> findAll();

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
