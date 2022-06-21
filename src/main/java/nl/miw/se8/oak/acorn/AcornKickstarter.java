package nl.miw.se8.oak.acorn;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

    private final PantryService pantryService;
    private final PantryProductService pantryProductService;
    private final ProductDefinitionService productDefinitionService;

    public AcornKickstarter(PantryService pantryService,
                            PantryProductService pantryProductService,
                            ProductDefinitionService productDefinitionService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
        this.productDefinitionService = productDefinitionService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedPantries();
        seedPantryProducts();
    }

    private void seedPantries() {
        pantryService.save(new Pantry("Pantry A"));
        pantryService.save(new Pantry("Pantry B"));
        pantryService.save(new Pantry("Pantry C"));
    }


    private void seedPantryProducts() {
        List<Pantry> pantries = pantryService.findAll();
        List<ProductDefinition> productDefinitions = productDefinitionService.findAll();

        for (Pantry pantry : pantries) {
            for (int i = 0; i < 10; i++) {
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
