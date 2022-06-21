package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.repository.AcornUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcornUserServiceImplementation implements AcornUserService, UserDetailsService {

    AcornUserRepository acornUserRepository;

    public AcornUserServiceImplementation(AcornUserRepository acornUserRepository) {
        this.acornUserRepository = acornUserRepository;
    }

    @Override
    public void save(AcornUser acornUser) {
        acornUserRepository.save(acornUser);
    }

    @Override
    public List<AcornUser> findAll() {
        return acornUserRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return acornUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with name " + username + " was not found.")
        );
    }

    @Override
    public Optional<AcornUser> findByUsername(String username) {
        return acornUserRepository.findByUsername(username);
    }
}
