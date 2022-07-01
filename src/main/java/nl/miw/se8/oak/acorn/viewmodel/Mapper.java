package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.AcornUser;
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

    public UserOverviewVM userToUserOverviewVM(AcornUser acornUser) {
        UserOverviewVM userOverviewVM = new UserOverviewVM();
        userOverviewVM.setId(acornUser.getId());
        userOverviewVM.setEmail(acornUser.getEmail());
        userOverviewVM.setName(acornUser.getName());
        return userOverviewVM;
    }

}
