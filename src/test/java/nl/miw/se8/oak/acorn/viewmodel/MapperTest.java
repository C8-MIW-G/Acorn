package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;



public class MapperTest {

    @Test
    @DisplayName("Tests if, when we convert a pantry to a viewmodel, the ViewModel is in fact created. " +
            "Then tests if all attributes are inherited correctly")
    void pantryToPantryViewmodelIdName() {
        Pantry testPantry = new Pantry();
        testPantry.setId(999L);
        testPantry.setName("Test");

        PantryViewmodelIdName testPantryMV = Mapper.pantryToPantryEditVM(testPantry);

        assertNotNull(testPantryMV, "A pantryViewModel object should be present, but it is not");
        assertEquals(999L, testPantryMV.getId(),
                "The id should be 999, however this is not the returned value");
        assertEquals("Test", testPantryMV.getName(),
                "The name 'Test' should be returned, however something else has been returned");
    }

    @Test
    @DisplayName("Tests conversion from pantryViewModelIdName to a pantry object.")
    void pantryViewmodelIdNameToPantry() {
        PantryViewmodelIdName testPantryVieModel = new PantryViewmodelIdName();
        testPantryVieModel.setId(999L);
        testPantryVieModel.setName("Test");

        Pantry testPantry = Mapper.pantryEditVMToPantry(testPantryVieModel);

        assertNotNull(testPantry, "A Pantry object should be present, but it is not");
        assertEquals(999L, testPantry.getId(), "The returned id value should be 999, " +
                "but something else was returned");
        assertEquals("Test", testPantry.getName(), "The name test should have been returned," +
                "But something else was returned");
    }

    @Test
    @DisplayName("Tests conversion from PantryUser to a PantryMemberVM " +
            "using id, name and email attributes")
    void pantryUserToPantryMemberVM() {
        PantryUser testPantryUser = new PantryUser();
        AcornUser testAcornUser = new AcornUser();

        testAcornUser.setEmail("test@test.com");
        testAcornUser.setName("Test");
        testPantryUser.setId(999L);
        testPantryUser.setUser(testAcornUser);

        PantryMemberVM testVM = Mapper.pantryUserToPantryMemberVM(testPantryUser);
        assertNotNull(testVM, "A viewModel should be present, but it is not");
        assertEquals(999L, testVM.getPantryUserId(),"The returned ID value should be 999L, " +
                "but something else was returned");
        assertEquals("Test", testVM.getAcornUserName(), "The returned string should be 'Test', " +
                "but something else was returned");
    }

    @Test
    @DisplayName("Tests conversion from Productdefinition to a ProductDefinitionOverviewViewModel " +
            "using id and name attributes")
    void productDefToProductViewModel() {
        Mapper mapper = new Mapper();
        ProductDefinition testProduct = new ProductDefinition();
        testProduct.setId(999L);
        testProduct.setName("Test");

        ProductsDefinitionOverviewViewModel testViewModel = mapper.productDefToProductDefVM(testProduct);

        assertNotNull(testViewModel, "A ProductDefinitionOverviewViewModel should be present, but it is not");
        assertEquals(999L, testProduct.getId(), "The returned ID value should be 999L," +
                "But something else was returned");
        assertEquals("Test", testProduct.getName(), "The name 'Test'  should have been returned," +
                "But something else was returned");
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

    @Test
    @DisplayName("Tests the mapper method that converts a pantryProduct to a PantryProductEditViewModel")
    void pantryProductToPantryProductEditViewModel() {
        PantryProduct pantryProduct = new PantryProduct();
        pantryProduct.setId(-1L);

        PantryProductEditViewModel pantryProductEditViewModel = Mapper.pantryProductToPantryProductEditViewModel(pantryProduct);

        assertInstanceOf(PantryProductEditViewModel.class, pantryProductEditViewModel);
        assertEquals(pantryProductEditViewModel.getId(), -1L,"Id is correct");
    }

    @Test
    @DisplayName("Tests the mapper function that converts a productDefinition VM back a product definition")
    void productDefinitionVMToProductDefinition() {
        ProductsDefinitionOverviewViewModel testProductDefVm = new ProductsDefinitionOverviewViewModel();
        testProductDefVm.setId(-1L);
        testProductDefVm.setName("Test");
        Mapper mapper = new Mapper();
        ProductDefinition  testProduct = mapper.productDefinitionVMToProductDefinition(testProductDefVm);

        assertNotNull(testProduct);
        assertEquals(-1L, testProduct.getId());
    }

}