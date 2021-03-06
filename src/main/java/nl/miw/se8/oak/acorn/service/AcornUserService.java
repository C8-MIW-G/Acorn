package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Role;
import nl.miw.se8.oak.acorn.viewmodel.UserOverviewVM;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface AcornUserService {
    Optional<AcornUser> findById(Long id);

    AcornUser save(AcornUser acornUser);

    List<AcornUser> findAll();

    Optional<AcornUser> findByEmail(String email);

    void deleteById(Long id);

    List<AcornUser> findByRolesContains(Role role);

    // Important for logging in
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    boolean moreThanOneSysAdmin();
    AcornUser updateCurrentUser(UserOverviewVM userOverviewVM);
    boolean emailInUseBySomeoneElse(UserOverviewVM userOverviewVM);

}
