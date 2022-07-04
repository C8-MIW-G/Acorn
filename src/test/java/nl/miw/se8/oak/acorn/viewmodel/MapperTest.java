package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class MapperTest {


    @Test
    @DisplayName("Tests if, when we convert a pantry to a viewmodel, the ViewModel is in fact created. " +
            "Then tests if all attributes are inherited correctly")
    void pantryToPantryViewmodelIdName() {
        Mapper mapper = new Mapper();
        Pantry testPantry = new Pantry(999L,"Test");
        PantryViewmodelIdName testPantryMV = mapper.pantryToPantryViewmodelIdName(testPantry);

        assertNotNull(testPantryMV, "A pantryViewModel object should be present, but it is not");
        assertEquals(999L, testPantryMV.getId(),
                "The id should be 999, however this is not the returned value");
        assertEquals("Test", testPantryMV.getName(),
                "The name 'Test' should be returned, however something else has been returned");
    }

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