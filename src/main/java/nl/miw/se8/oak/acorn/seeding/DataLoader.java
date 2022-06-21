package nl.miw.se8.oak.acorn.seeding;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Sylvia Kazakou
 * Seeds productDefinitions upon application start
 */

@Component
public class DataLoader {

    private final ProductDefinitionService productDefinitionService;
    private final AcornUserService userService;

    public DataLoader(ProductDefinitionService productDefinitionService, AcornUserService userService) {
        this.productDefinitionService = productDefinitionService;
        this.userService = userService;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsers();
        seedProductDefinitions();
    }

    private void seedUsers() {
            if(userService.findAll().size() == 0) {
                userService.save(new AcornUser("test", "1234"));
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
}
