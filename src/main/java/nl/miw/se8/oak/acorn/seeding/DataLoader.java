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
    private final RequiredProductService requiredProductService;

    public DataLoader(ProductDefinitionService productDefinitionService,
                      AcornUserService userService,
                      PantryService pantryService,
                      PantryUserService pantryUserService,
                      PantryProductService pantryProductService,
                      RoleService roleService,
                      PrivilegeService privilegeService,
                      PasswordEncoder passwordEncoder,
                      RequiredProductService requiredProductService) {
        this.productDefinitionService = productDefinitionService;
        this.userService = userService;
        this.pantryService = pantryService;
        this.pantryUserService = pantryUserService;
        this.pantryProductService = pantryProductService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.passwordEncoder = passwordEncoder;
        this.requiredProductService = requiredProductService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsers();
        seedPantries();
        seedPantryUsers();
        seedProductDefinitions();
        seedPantryProducts();
    }

    private void seedUsers() {
            if(userService.findAll().size() == 0) {
                Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
                Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
                List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
                Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
                Role userRole = createRoleIfNotFound("ROLE_USER", List.of(readPrivilege));

                AcornUser admin = new AcornUser();
                admin.setEmail("admin@acorn.com");
                admin.setPassword(passwordEncoder.encode("Admin"));
                admin.setName("Admininstrator");
                admin.setRoles(List.of(adminRole));
                userService.save(admin);

                AcornUser user = new AcornUser();
                user.setEmail("user@acorn.com");
                user.setPassword(passwordEncoder.encode("User"));
                user.setName("user");
                user.setRoles(List.of(userRole));
                userService.save(user);

                AcornUser wicher = new AcornUser();
                wicher.setEmail("wicher@acorn.com");
                wicher.setPassword(passwordEncoder.encode("Wicher"));
                wicher.setName("Wicher");
                wicher.setRoles(List.of(userRole));
                userService.save(wicher);

                AcornUser thijs = new AcornUser();
                thijs.setEmail("thijs@acorn.com");
                thijs.setPassword(passwordEncoder.encode("Thijs"));
                thijs.setName("Thijs");
                thijs.setRoles(List.of(userRole));
                userService.save(thijs);

                // Presentation user for Wicher
                AcornUser toby = new AcornUser();
                toby.setEmail("toby@email.com");
                toby.setPassword(passwordEncoder.encode("Toby"));
                toby.setName("Toby");
                toby.setRoles(List.of(userRole));
                userService.save(toby);

                AcornUser rynaert = new AcornUser();
                rynaert.setEmail("rynaert@email.com");
                rynaert.setPassword(passwordEncoder.encode("Rynaert"));
                rynaert.setName("Rynaert");
                rynaert.setRoles(List.of(userRole));
                userService.save(rynaert);

                AcornUser ishmail = new AcornUser();
                ishmail.setEmail("ishmail@email.com");
                ishmail.setPassword(passwordEncoder.encode("Ishmail"));
                ishmail.setName("Ishmail");
                ishmail.setRoles(List.of(userRole));
                userService.save(ishmail);

                AcornUser derk = new AcornUser();
                derk.setEmail("derk@email.com");
                derk.setPassword(passwordEncoder.encode("Derk"));
                derk.setName("Derk");
                derk.setRoles(List.of(userRole));
                userService.save(derk);

                AcornUser merel = new AcornUser();
                merel.setEmail("merel@email.com");
                merel.setPassword(passwordEncoder.encode("Merel"));
                merel.setName("Merel");
                merel.setRoles(List.of(userRole));
                userService.save(merel);

                AcornUser aurelie = new AcornUser();
                aurelie.setEmail("aurelie@email.com");
                aurelie.setPassword(passwordEncoder.encode("Aurelie"));
                aurelie.setName("Aurelie");
                aurelie.setRoles(List.of(userRole));
                userService.save(aurelie);

                AcornUser maria = new AcornUser();
                maria.setEmail("maria@email.com");
                maria.setPassword(passwordEncoder.encode("Maria"));
                maria.setName("Maria");
                maria.setRoles(List.of(userRole));
                userService.save(maria);

                AcornUser guus = new AcornUser();
                guus.setEmail("guus@email.com");
                guus.setPassword(passwordEncoder.encode("Guus"));
                guus.setName("Guus");
                guus.setRoles(List.of(userRole));
                userService.save(guus);

            }
    }

    private void seedPantries() {
        if (pantryService.findAll().size() == 0) {
            String[] pantryNames = {
                "Wicher's Pantry",
                "Thijs' Pantry",
                "VV Oostlaren",
                "MIW Keukentje"
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
            pantryUserService.save(new PantryUser(userService.findByEmail("wicher@acorn.com").get(), pantryService.findByName("Wicher's Pantry").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("wicher@acorn.com").get(), pantryService.findByName("VV Oostlaren").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("wicher@acorn.com").get(), pantryService.findByName("MIW Keukentje").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("thijs@acorn.com").get(), pantryService.findByName("Thijs' Pantry").get(), true));
            pantryUserService.save(new PantryUser(userService.findByEmail("thijs@acorn.com").get(), pantryService.findByName("MIW Keukentje").get(), true));

            pantryUserService.save(new PantryUser(userService.findByEmail("toby@email.com").get(), pantryService.findByName("VV Oostlaren").get(), false));
            pantryUserService.save(new PantryUser(userService.findByEmail("rynaert@email.com").get(), pantryService.findByName("VV Oostlaren").get(), false));
            pantryUserService.save(new PantryUser(userService.findByEmail("ishmail@email.com").get(), pantryService.findByName("VV Oostlaren").get(), false));
            pantryUserService.save(new PantryUser(userService.findByEmail("derk@email.com").get(), pantryService.findByName("VV Oostlaren").get(), false));
            pantryUserService.save(new PantryUser(userService.findByEmail("merel@email.com").get(), pantryService.findByName("VV Oostlaren").get(), false));
            pantryUserService.save(new PantryUser(userService.findByEmail("aurelie@email.com").get(), pantryService.findByName("VV Oostlaren").get(), false));
        }
    }

    private void seedProductDefinitions() {
        if (productDefinitionService.findAll().size() == 0) {
            String[] products = {
                    "Aardbeien 500g",
                    "Blauwe Bessen 300g",
                    "Druiven Wit Pitloos 500g",
                    "Galia Meloen",
                    "Ananas",
                    "Frambozen 125g",
                    "Mandarijnen 1kg",
                    "Appels Jonagold 1kg",
                    "Watermeloen",
                    "Gold Kiwifruit 6x",
                    "Mini Krieltjes Bistro 600g",
                    "Aardappelen Kruimig 1kg",
                    "Aardappelen Kruimig 3kg",
                    "Aardappelen Vastkokend 1kg",
                    "Aardappelen Vastkokend 3kg",
                    "Aardappelschijfjes 600g",
                    "Zilvervliesrijst 400g",
                    "Verse Gnocchi 500g",
                    "Fusili 500g",
                    "Fusili 500g volkoren",
                    "Tomatenblokjes 400g",
                    "Tomatenpuree 70g",
                    "Pesto alla Genovese 185g",
                    "Kroepoek Cassave 75g",
                    "Kruidige Ketchup 580g",
                    "Fijne Augurk Zoetzuur 370g",
                    "Zilver Uitjes Zoetzuur 320g",
                    "Mayonaise 650ml",
                    "Ketchup 400ml",
                    "Olijfolie Classico 1L",
                    "Zonnebloemolie 1L",
                    "Bruin Tijgerbrood",
                    "Bruin Tijgerbrood (half)",
                    "Roomboter Croissants",
                    "Brood Fijn Volkoren",
                    "Brood Fijn Volkoren (half)",
                    "Smeuïge Pindakaas 350g",
                    "Duo Hagelslag 600g",
                    "Aardbei Extra Jam 440g",
                    "Nutella 400g",
                    "Pindakaas 400g",
                    "Pindakaas 600g",
                    "Appelstroop 450g",
                    "Bier 6x300ml",
                    "Bier krat 12x300ml",
                    "Bier krat 24x300ml",
                    "Bier 6x300ml 0.0% Alcoholvrij",
                    "Bier krat 12x300ml 0.0% Alcoholvrij",
                    "Bier krat 24x300ml 0.0% Alcoholvrij",
                    "Wijn Merlot 750ml",
                    "Wijn Cabernet Sauvignon 750ml",
                    "Wijn Chardonnay 750ml",
                    "Wijn Pinot Grigio 750ml",
                    "Appelsap 1,5L",
                    "Suiker 1kg",
                    "Sinaasappelsap 1,5L",
                    "Ice Tea 1,5L",
                    "Filterkoffie 500g",
                    "Siroop 1L",
                    "Koffiemelk 500ml",
                    "Koffiepads 36 stuks",
                    "Thee 20 stuks",
                    "Frisdrank 1L",
                    "Diepvries",
                    "Frambozen bevroren 1 kilo",
                    "Spinazie á la crème",
                    "Frikadellen 20x",
                    "Gehaktballen 8x",
                    "Tricolore Roomijs 1L",
                    "Friet 2kg",
                    "Bitterballen 100 stuks",
                    "Gemengd fruit bevroren 1kg",
                    "Perenijsjes 10 stuks",
                    "IJstaart 800g",
                    "Paprika chips 250g",
                    "Naturel chips 250g",
                    "Ketchup chips 200g",
                    "Borrelnoten 300g",
                    "Pistachenoten 150g",
                    "Gevulde koeken 8x",
                    "Stroopwafels 12 stuks",
                    "Appeltaart 600g",
                    "Slagroomtaart 550g",
                    "Mokkagebak 550g",
                    "Droge worsten 6 stuks",
                    "Champignons 250g",
                    "Snoeptomaatjes 200g",
                    "Paprika",
                    "Maaltijsalade 400g",
                    "Witte bonen 360g",
                    "Bruine bonen 340g",
                    "Kikkererwten 400g",
                    "Linzen 200g",
                    "Portobello",
                    "Gehakt half-om-half 500g",
                    "Braadworsten 4 stuks",
                    "Kipfilet 1kg",
                    "Kip Krokant Schnitzels 2x",
                    "Chipolata Worstjes 6x",
                    "Zigeunerschnitzels 2x",
                    "Shaormavlees (kip) 500g",
                    "Slavinken 4x",
                    "Rundervinken 4x",
                    "Speklappen 600gr"
            };

            for (String product : products) {
                productDefinitionService.save(new ProductDefinition(product));
            }
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

            Pantry VVOostlaren = pantryService.findByName("VV Oostlaren").get();
            for (int i = 0; i < 3; i++) {
                int randomProduct2 = (int) (Math.random() * productDefinitions.size());
                int randomDate2 = (int) (Math.random() * 31);
                PantryProduct pantryProduct2 = new PantryProduct(
                        VVOostlaren,
                        productDefinitions.get(randomProduct2),
                        LocalDate.now().minusDays(randomDate2));
                pantryProductService.save(pantryProduct2);
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

}
