package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {

    @Test
    void pantryToAdminPantryOverviewVM() {
        // Arrange
        Pantry pantry = new Pantry();
        pantry.setId(11L);
        pantry.setName("testPantry");

        // Act
        AdminPantryOverviewVM adminPantryOverviewVM = Mapper.pantryToAdminPantryOverviewVM(pantry);

        // Assert
        assertInstanceOf(AdminPantryOverviewVM.class, adminPantryOverviewVM);
        assertEquals(adminPantryOverviewVM.getId(), 11L);
        assertEquals(adminPantryOverviewVM.getName(), "testPantry");
    }

    @Test
    void userToUserOverviewVM() {
        AcornUser user = new AcornUser();
        user.setId(22L);
        user.setEmail("testUser@testUser.com");
        user.setName("testUser");

        UserOverviewVM userOverviewVM = Mapper.userToUserOverviewVM(user);

        assertInstanceOf(UserOverviewVM.class, userOverviewVM);
        assertEquals(userOverviewVM.getId(), 22L);
        assertEquals(userOverviewVM.getEmail(), "testUser@testUser.com");
        assertEquals(userOverviewVM.getName(), "testUser");
    }
}