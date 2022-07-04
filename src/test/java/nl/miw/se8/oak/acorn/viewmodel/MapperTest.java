package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.Pantry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static nl.miw.se8.oak.acorn.viewmodel.Mapper.pantryToPantryViewmodelIdName;

public class MapperTest {


    @Test
    @DisplayName("Tests if, when we convert a pantry to a viewmodel, the ViewModel is in fact created")
    void pantryToPantryViewmodelIdName() {
        Mapper mapper = new Mapper();
        Pantry testPantry = new Pantry(999L,"Test");
        PantryViewmodelIdName testPantryMV = mapper.pantryToPantryViewmodelIdName(testPantry);

        assertNotNull(testPantryMV, "A pantryViewModel object should be present, but it is not");
    }

    @Test
    @DisplayName("Tests if a pantry gets converted to a viewmodel with the proper ID")
    void pantryToPantryViewmodelIdNameIdTest() {
        Mapper mapper = new Mapper();
        Pantry testPantry = new Pantry(999L,"Test");
        PantryViewmodelIdName testPantryMV = mapper.pantryToPantryViewmodelIdName(testPantry);

        assertEquals(999L, testPantryMV.getId(),
                "The id should be 999, however this is not the returned value");
    }

    @Test
    @DisplayName("Tests if a pantry gets converted to a viewmodel with the proper Name")
    void pantryToPantryViewmodelIdNameNameTest() {
        Mapper mapper = new Mapper();
        Pantry testPantry = new Pantry(999L,"Test");
        PantryViewmodelIdName testPantryMV = mapper.pantryToPantryViewmodelIdName(testPantry);

        assertEquals("Test", testPantryMV.getName(),
                "The name 'Test' should be returned, however something else has been returned");
    }


}
