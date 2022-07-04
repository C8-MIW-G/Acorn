package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.ProductDefinition;

public class Mapper {

    public static PantryViewmodelIdName pantryToPantryViewmodelIdName(Pantry pantry) {
        PantryViewmodelIdName pantryDTO = new PantryViewmodelIdName();
        pantryDTO.setId(pantry.getId());
        pantryDTO.setName(pantry.getName());
        return pantryDTO;
    }

    public Pantry pantryViewmodelIdNameToPantry(PantryViewmodelIdName pantryEditViewmodel) {
        Pantry pantry = new Pantry();
        pantry.setId(pantryEditViewmodel.getId());
        pantry.setName(pantryEditViewmodel.getName());
        return pantry;
    }

    public ProductsDefinitionOverviewViewModel productDefToProductViewModel(ProductDefinition pD) {
        ProductsDefinitionOverviewViewModel productsDefinitionOverviewViewModel = new ProductsDefinitionOverviewViewModel();
        productsDefinitionOverviewViewModel.setId(pD.getId());
        productsDefinitionOverviewViewModel.setName(pD.getName());
        return productsDefinitionOverviewViewModel;
    }

    public static UserOverviewVM userToUserOverviewVM(AcornUser acornUser) {
        UserOverviewVM userOverviewVM = new UserOverviewVM();
        userOverviewVM.setId(acornUser.getId());
        userOverviewVM.setEmail(acornUser.getEmail());
        userOverviewVM.setName(acornUser.getName());
        return userOverviewVM;
    }

    public AcornUser userOverviewVMToUser(UserOverviewVM userOverviewVM) {
        AcornUser acornUser = new AcornUser();
        acornUser.setEmail(userOverviewVM.getEmail());
        acornUser.setName(userOverviewVM.getName());
        return acornUser;
    }

    public static AdminPantryOverviewVM pantryToAdminPantryOverviewVM(Pantry pantry) {
        AdminPantryOverviewVM adminPantryOverviewVM = new AdminPantryOverviewVM();
        adminPantryOverviewVM.setId(pantry.getId());
        adminPantryOverviewVM.setName(pantry.getName());
        return adminPantryOverviewVM;

    }
}
