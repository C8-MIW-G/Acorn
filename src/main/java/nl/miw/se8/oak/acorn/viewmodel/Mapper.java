package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.Pantry;

public class Mapper {


    public PantryEditViewmodel pantryToPantryEditViewmodel(Pantry pantry) {
        PantryEditViewmodel pantryDTO = new PantryEditViewmodel();
        pantryDTO.setId(pantry.getId());
        pantryDTO.setName(pantry.getName());
        return pantryDTO;
    }

}
