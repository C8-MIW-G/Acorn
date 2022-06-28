package nl.miw.se8.oak.acorn.seeding;

import nl.miw.se8.oak.acorn.model.*;
import nl.miw.se8.oak.acorn.service.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Sylvia Kazakou
 * Seeds productDefinitions upon application start
 */

@Component
public class DataLoader {

    private final ProductDefinitionService productDefinitionService;
    private final AcornUserService userService;
    private final PantryService pantryService;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(ProductDefinitionService productDefinitionService,
                      AcornUserService userService,
                      PantryService pantryService,
                      RoleService roleService,
                      PrivilegeService privilegeService,
                      PasswordEncoder passwordEncoder) {
        this.productDefinitionService = productDefinitionService;
        this.userService = userService;
        this.pantryService = pantryService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsers();
        seedProductDefinitions();
        seedPantries();
    }

    private void seedUsers() {
            if(userService.findAll().size() == 0) {
                Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
                Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
                List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
                Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
                Role userRole = createRoleIfNotFound("ROLE_USER", List.of(readPrivilege));

                AcornUser admin = new AcornUser();
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setName("admin");
                admin.setRoles(List.of(adminRole));
                userService.save(admin);

                AcornUser user = new AcornUser();
                user.setEmail("user@user.com");
                user.setPassword(passwordEncoder.encode("user"));
                user.setName("test");
                user.setRoles(List.of(userRole));
                userService.save(user);
            }
    }

    private void seedProductDefinitions() {
        if (productDefinitionService.findAll().size() == 0) {
            productDefinitionService.save( new ProductDefinition("Bread"));
            productDefinitionService.save( new ProductDefinition("Beans"));
            productDefinitionService.save( new ProductDefinition("Patatas"));
            productDefinitionService.save( new ProductDefinition("Tomato paste"));
            productDefinitionService.save( new ProductDefinition("Peas"));
            productDefinitionService.save( new ProductDefinition("Chips"));
            productDefinitionService.save( new ProductDefinition("Chocolate"));
            productDefinitionService.save( new ProductDefinition("Salsa"));
            productDefinitionService.save( new ProductDefinition("Pepitas"));
            productDefinitionService.save( new ProductDefinition("Roquefort"));
            productDefinitionService.save( new ProductDefinition("Brie"));
            productDefinitionService.save( new ProductDefinition("Paprika"));
            productDefinitionService.save( new ProductDefinition("Almond yoghurt"));
            productDefinitionService.save( new ProductDefinition("Rice crackers"));
            productDefinitionService.save( new ProductDefinition("Tagliatelle"));
            productDefinitionService.save( new ProductDefinition("Sun dried tomatoes"));
            productDefinitionService.save( new ProductDefinition("Kalamata olives"));
            productDefinitionService.save( new ProductDefinition("Ntolmadakia"));
            productDefinitionService.save( new ProductDefinition("Peanut butter"));
            productDefinitionService.save( new ProductDefinition("Jam"));
        }
    }

    private void seedPantries() {
        if (pantryService.findAll().size() == 0) {
            pantryService.save(new Pantry("Sylvia's Pantry"));
            pantryService.save(new Pantry("Wicher's Pantry"));
            pantryService.save(new Pantry("Thijs' Pantry"));
        }
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> optionalPrivilege = privilegeService.findByName(name);
        if (optionalPrivilege.isPresent()) {
            return optionalPrivilege.get();
        }

        Privilege privilege = new Privilege(name);
        return privilegeService.save(privilege);
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Optional<Role> optionalRole = roleService.findByName(name);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }

        Role role = new Role(name);
        role.setPrivileges(privileges);
        return roleService.save(role);
    }

}
