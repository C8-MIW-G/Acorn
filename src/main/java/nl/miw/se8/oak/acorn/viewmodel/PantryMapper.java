package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.Pantry;

public class PantryMapper {


    public PantryEditDto pantrytoDto(Pantry pantry) {
        PantryEditDto pantryDTO = new PantryEditDto();
        pantryDTO.setId(pantry.getId());
        pantryDTO.setName(pantry.getName());
        return pantryDTO;
    }

}
