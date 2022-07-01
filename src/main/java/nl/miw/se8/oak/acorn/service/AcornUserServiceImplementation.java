package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Role;
import nl.miw.se8.oak.acorn.repository.AcornUserRepository;
import nl.miw.se8.oak.acorn.viewmodel.UserOverviewVM;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AcornUserServiceImplementation implements AcornUserService, UserDetailsService {

    AcornUserRepository acornUserRepository;
    RoleService roleService;

    public AcornUserServiceImplementation(AcornUserRepository acornUserRepository, RoleService roleService) {
        this.acornUserRepository = acornUserRepository;
        this.roleService = roleService;
    }

    @Override
    public Optional<AcornUser> findById(Long id) {
        return acornUserRepository.findById(id);
    }

    @Override
    public AcornUser save(AcornUser acornUser) {
        return acornUserRepository.save(acornUser);
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
    public void deleteById(Long id) {
        acornUserRepository.deleteById(id);
    }

    @Override
    public List<AcornUser> findByRolesContains(Role role) {
        return acornUserRepository.findByRolesContains(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AcornUser> optionalAcornUser = acornUserRepository.findByEmail(username);
        if (optionalAcornUser.isEmpty()) {
            throw new UsernameNotFoundException("User with name " + username + " was not found.");
        }
        return optionalAcornUser.get();
    }

    @Override
    public boolean moreThanOneSysAdmin() {
        Optional<Role> optionalAdminRole = roleService.findByName(Role.ROLE_ADMIN);
        if (optionalAdminRole.isPresent()) {
            List<AcornUser> sysAdmins = findByRolesContains(optionalAdminRole.get());
            return sysAdmins.size() > 1;
        }
        return false;
    }

    @Override
    public AcornUser updateCurrentUser(UserOverviewVM userOverviewVM) {
        Optional<AcornUser> acornUser = findById(userOverviewVM.getId());
        if (acornUser.isPresent()) {
            acornUser.get().setEmail(userOverviewVM.getEmail());
            acornUser.get().setName(userOverviewVM.getName());
            return save(acornUser.get());
        }
        return null;
    }

    @Override
    public boolean emailInUseBySomeoneElse(UserOverviewVM userOverviewVM) {
        Optional<AcornUser> dbAcornUser = findByEmail(userOverviewVM.getEmail());
        if (dbAcornUser.isPresent()) {
            if (!Objects.equals(dbAcornUser.get().getId(), userOverviewVM.getId())) {
                return true;
            }
        }
        return false;
    }

}
