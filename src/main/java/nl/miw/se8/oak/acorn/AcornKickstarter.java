package nl.miw.se8.oak.acorn;

import nl.miw.se8.oak.acorn.model.*;
import nl.miw.se8.oak.acorn.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 21-6-2022
 *
 * Loads a set of Pantries and PantryProducts into the database
 */
@SpringBootApplication
public class AcornKickstarter implements CommandLineRunner {

    private final AcornUserService acornUserService;
    private final PantryService pantryService;
    private final PantryUserService pantryUserService;
    private final PantryProductService pantryProductService;
    private final ProductDefinitionService productDefinitionService;
    private final PasswordEncoder passwordEncoder;

    public AcornKickstarter(AcornUserService acornUserService,
                            PantryService pantryService,
                            PantryUserService pantryUserService,
                            PantryProductService pantryProductService,
                            ProductDefinitionService productDefinitionService,
                            PasswordEncoder passwordEncoder) {
        this.acornUserService = acornUserService;
        this.pantryService = pantryService;
        this.pantryUserService = pantryUserService;
        this.pantryProductService = pantryProductService;
        this.productDefinitionService = productDefinitionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean runThis = false;
        if (runThis) {
            seedUsers();
            seedPantries();
            seedPantryUsers();
            seedPantryProducts();
        }
    }

    private void seedUsers() {
        acornUserService.save(new AcornUser("Sylvia", passwordEncoder.encode("1234")));
        acornUserService.save(new AcornUser("Wicher", passwordEncoder.encode("1234")));
        acornUserService.save(new AcornUser("Thijs", passwordEncoder.encode("1234")));
    }

    private void seedPantries() {
        pantryService.save(new Pantry("Sylvia's Pantry"));
        pantryService.save(new Pantry("Wicher's Pantry"));
        pantryService.save(new Pantry("Thijs' Pantry"));
    }

    private void seedPantryUsers() {
//        pantryUserService.save(new PantryUser(acornUserService.findByUsername("Sylvia").get(), pantryService.findByEmail("Sylvia's Pantry").get()));
//        pantryUserService.save(new PantryUser(acornUserService.findByUsername("Wicher").get(), pantryService.findByEmail("Wicher's Pantry").get()));
//        pantryUserService.save(new PantryUser(acornUserService.findByUsername("Thijs").get(), pantryService.findByEmail("Thijs' Pantry").get()));
    }

    private void seedPantryProducts() {
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
