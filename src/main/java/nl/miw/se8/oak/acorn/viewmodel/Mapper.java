package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.ProductDefinition;

public class Mapper {


    public PantryViewmodelIdName pantryToPantryViewmodelIdName(Pantry pantry) {
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

    public ProductsViewModel productDefToProductViewModel(ProductDefinition pD) {
        ProductsViewModel productsViewModel = new ProductsViewModel();
        productsViewModel.setId(pD.getId());
        productsViewModel.setName(pD.getName());
        return productsViewModel;
    }

    public UserOverviewVM userToUserOverviewVM(AcornUser acornUser) {
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

}
