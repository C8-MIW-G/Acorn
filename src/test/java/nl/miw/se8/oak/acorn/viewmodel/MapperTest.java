package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.Pantry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void pantryToAdminPantryOverviewVM() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.setId(99L);
        pantry.setName("testPantry");

        // Act
        AdminPantryOverviewVM adminPantryOverviewVM = Mapper.pantryToAdminPantryOverviewVM(pantry);

        // Assert
        assertInstanceOf(AdminPantryOverviewVM.class, adminPantryOverviewVM);
        assertEquals(adminPantryOverviewVM.getId(), 99L);
        assertEquals(adminPantryOverviewVM.getName(), "testPantry");
    }

}