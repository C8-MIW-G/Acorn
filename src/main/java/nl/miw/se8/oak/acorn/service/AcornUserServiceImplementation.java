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
    public Optional<AcornUser> findById(Long id) {
        return acornUserRepository.findById(id);
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
    public Optional<AcornUser> findByEmail(String username) {
        return acornUserRepository.findByEmail(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AcornUser> optionalAcornUser = acornUserRepository.findByEmail(username);
        if (optionalAcornUser.isEmpty()) {
            throw new UsernameNotFoundException("User with name " + username + " was not found.");
        }
        return optionalAcornUser.get();
    }

}
