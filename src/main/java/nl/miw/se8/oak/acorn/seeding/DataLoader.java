package nl.miw.se8.oak.acorn.seeding;

import nl.miw.se8.oak.acorn.model.*;
import nl.miw.se8.oak.acorn.service.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader {

    private final ProductDefinitionService productDefinitionService;
    private final AcornUserService userService;
    private final PantryService pantryService;
    private final PantryUserService pantryUserService;
    private final PantryProductService pantryProductService;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingListProductService shoppingListProductService;

    public DataLoader(ProductDefinitionService productDefinitionService,
                      AcornUserService userService,
                      PantryService pantryService,
                      PantryUserService pantryUserService,
                      PantryProductService pantryProductService,
                      RoleService roleService,
                      PrivilegeService privilegeService,
                      PasswordEncoder passwordEncoder,
                      ShoppingListProductService shoppingListProductService) {
        this.productDefinitionService = productDefinitionService;
        this.userService = userService;
        this.pantryService = pantryService;
        this.pantryUserService = pantryUserService;
        this.pantryProductService = pantryProductService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.passwordEncoder = passwordEncoder;
        this.shoppingListProductService = shoppingListProductService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsers();
        seedPantries();
        seedPantryUsers();
        seedProductDefinitions();
        seedPantryProducts();
        seedShoppingListProducts();
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
                admin.setName("Admininstrator");
                admin.setRoles(List.of(adminRole));
                userService.save(admin);

                AcornUser sylvia = new AcornUser();
                sylvia.setEmail("Sylvia");
                sylvia.setPassword(passwordEncoder.encode("Sylvia"));
                sylvia.setName("Sylvia");
                sylvia.setRoles(List.of(adminRole));
                userService.save(sylvia);

                AcornUser wicher = new AcornUser();
                wicher.setEmail("Wicher");
                wicher.setPassword(passwordEncoder.encode("Wicher"));
                wicher.setName("Wicher");
                wicher.setRoles(List.of(adminRole));
                userService.save(wicher);

                AcornUser thijs = new AcornUser();
                thijs.setEmail("Thijs");
                thijs.setPassword(passwordEncoder.encode("Thijs"));
                thijs.setName("Thijs");
                thijs.setRoles(List.of(adminRole));
                userService.save(thijs);

                AcornUser user = new AcornUser();
                user.setEmail("user@user.com");
                user.setPassword(passwordEncoder.encode("user"));
                user.setName("user");
                user.setRoles(List.of(userRole));
                userService.save(user);
            }
    }

    private void seedPantries() {
        if (pantryService.findAll().size() == 0) {
            String[] pantryNames = {
                    "Sylvia's Pantry",
                    "Wicher's Pantry",
                    "Thijs' Pantry",
                    "Sportclub Rijssen",
                    "Korfbalvereniging SDO",
                    "OBS De Cirkel",
                    "GLV Idun",
                    "Keukentje MIW",
                    "Bridgeclub Harde Schoppen",
                    "Dansvereniging Blue Toes",
                    "Liminoid",
                    "Huize Ill Castello"
            };

            Pantry pantry = new Pantry();

            for (String name : pantryNames) {
                pantry.setName(name);
                pantryService.save(pantry);
            }
        }
    }

    private void seedPantryUsers() {
        if (pantryUserService.findAll().size() == 0) {
            pantryUserService.save(new PantryUser(userService.findByEmail("Sylvia").get(), pantryService.findByName("Sylvia's Pantry").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("Wicher").get(), pantryService.findByName("Wicher's Pantry").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Thijs' Pantry").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Sylvia's Pantry").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Wicher's Pantry").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Sportclub Rijssen").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Korfbalvereniging SDO").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("GLV Idun").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Keukentje MIW").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Thijs").get(), pantryService.findByName("Liminoid").get(), false));       // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("Sylvia").get(), pantryService.findByName("Wicher's Pantry").get(), false));      // Added for testing purposes for pantruUsers List in Pantry
            pantryUserService.save(new PantryUser(userService.findByEmail("user@user.com").get(), pantryService.findByName("Wicher's Pantry").get(), false));      // Added for testing purposes for pantruUsers List in Pantry
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

    private void seedPantryProducts() {
        if (pantryProductService.findAll().size() == 0) {
            List<Pantry> pantries = pantryService.findAll();
            List<ProductDefinition> productDefinitions = productDefinitionService.findAll();

            for (Pantry pantry : pantries) {
                for (int i = 0; i < 20; i++) {
                    int randomProduct = (int) (Math.random() * productDefinitions.size());
                    int randomDate = (int) (Math.random() * 31);
                    PantryProduct pantryProduct = new PantryProduct(
                            pantry,
                            productDefinitions.get(randomProduct),
                            LocalDate.now().plusDays(randomDate));
                    pantryProductService.save(pantryProduct);
                }
            }
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

    private void seedShoppingListProducts() {
        List<Pantry> allPantries = pantryService.findAll();
        List<ProductDefinition> allProductDefinitions = productDefinitionService.findAll();

        for (Pantry pantry : allPantries) {
            for (int i = 0; i < 10; i++) {
                ShoppingListProduct shoppingListProduct = new ShoppingListProduct(pantry, allProductDefinitions.get((int) (Math.random() * allProductDefinitions.size())));
                shoppingListProductService.save(shoppingListProduct);
            }
        }
    }

}
