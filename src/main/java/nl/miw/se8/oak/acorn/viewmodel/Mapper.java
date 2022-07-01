package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.Pantry;

public class Mapper {


    public PantryEditViewmodel pantryToPantryEditViewmodel(Pantry pantry) {
        PantryEditViewmodel pantryDTO = new PantryEditViewmodel();
        pantryDTO.setId(pantry.getId());
        pantryDTO.setName(pantry.getName());
        return pantryDTO;
    }

    public Pantry pantryEditViewmodelToPantry(PantryEditViewmodel pantryEditViewmodel) {
        Pantry pantry = new Pantry();
        pantry.setId(pantryEditViewmodel.getId());
        pantry.setName(pantryEditViewmodel.getName());
        return pantry;
    }

}
