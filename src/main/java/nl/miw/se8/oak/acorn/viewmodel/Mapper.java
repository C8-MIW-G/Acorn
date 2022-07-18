package nl.miw.se8.oak.acorn.viewmodel;

import nl.miw.se8.oak.acorn.dto.ProductDefinitionDTO;
import nl.miw.se8.oak.acorn.model.*;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.PantryUserService;

import java.util.Optional;

public class Mapper {

    PantryUserService pantryUserService;

    AcornUserService acornUserService;
    PantryService pantryService;

    public static PantryViewmodelIdName pantryToPantryEditVM(Pantry pantry) {
        PantryViewmodelIdName pantryEditVM = new PantryViewmodelIdName();
        pantryEditVM.setId(pantry.getId());
        pantryEditVM.setName(pantry.getName());
        return pantryEditVM;
    }

    public static Pantry pantryEditVMToPantry(PantryViewmodelIdName pantryEditVM) {
        Pantry pantry = new Pantry();
        pantry.setId(pantryEditVM.getId());
        pantry.setName(pantryEditVM.getName());
        return pantry;
    }

    public ProductsDefinitionOverviewViewModel productDefToProductDefVM(ProductDefinition productDefinition) {
        ProductsDefinitionOverviewViewModel productsDefinitionOverviewViewModel = new ProductsDefinitionOverviewViewModel();
        productsDefinitionOverviewViewModel.setId(productDefinition.getId());
        productsDefinitionOverviewViewModel.setName(productDefinition.getName());
        return productsDefinitionOverviewViewModel;
    }

    public ProductDefinition productDefinitionVMToProductDefinition(ProductsDefinitionOverviewViewModel productDefinitionVM) {
        ProductDefinition productDefinition = new ProductDefinition();
        productDefinition.setId(productDefinitionVM.getId());
        productDefinition.setName(productDefinitionVM.getName());
        return productDefinition;
    }

    public static UserOverviewVM userToUserOverviewVM(AcornUser acornUser) {
        UserOverviewVM userOverviewVM = new UserOverviewVM();
        userOverviewVM.setId(acornUser.getId());
        userOverviewVM.setEmail(acornUser.getEmail());
        userOverviewVM.setName(acornUser.getName());
        return userOverviewVM;
    }

    public static AdminPantryOverviewVM pantryToAdminPantryOverviewVM(Pantry pantry) {
        AdminPantryOverviewVM adminPantryOverviewVM = new AdminPantryOverviewVM();
        adminPantryOverviewVM.setId(pantry.getId());
        adminPantryOverviewVM.setName(pantry.getName());
        return adminPantryOverviewVM;
    }

    public static PantryProductEditViewModel pantryProductToPantryProductEditViewModel(PantryProduct pantryProduct) {
        PantryProductEditViewModel pantryProductEditViewModel = new PantryProductEditViewModel();
        pantryProductEditViewModel.setId(pantryProduct.getId());

        if (pantryProduct.getId() != PantryProduct.DEFAULT_ID) {
            pantryProductEditViewModel.setPantryId(pantryProduct.getPantry().getId());
            pantryProductEditViewModel.setProductDefinitionId(pantryProduct.getProductDefinition().getId());
            pantryProductEditViewModel.setExpirationDate(pantryProduct.getExpirationDate());
        }

        return pantryProductEditViewModel;
    }

    public static AcornUser userRegisterVMToUser(UserRegisterVM userRegisterVM) {
        AcornUser acornUser = new AcornUser();
        acornUser.setEmail(userRegisterVM.getEmail());
        acornUser.setName(userRegisterVM.getName());
        acornUser.setPassword(userRegisterVM.getPassword());
        return acornUser;
    }

    public static UserEditVM userToUserEditVM(AcornUser acornUser) {
        UserEditVM userEditVM = new UserEditVM();
        userEditVM.setId(acornUser.getId());
        userEditVM.setEmail(acornUser.getEmail());
        userEditVM.setName(acornUser.getName());
        return userEditVM;
    }

    public static PantryMemberVM pantryUserToPantryMemberVM(PantryUser pantryUser) {
        PantryMemberVM pantryMemberVM = new PantryMemberVM();
        pantryMemberVM.setPantryUserId(pantryUser.getId());
        pantryMemberVM.setAcornUserName(pantryUser.getUser().getName());
        pantryMemberVM.setUserId(pantryUser.getUser().getId());
        pantryMemberVM.setIsAdministrator(pantryUser.isAdministrator());
        return pantryMemberVM;
    }

    public AddPantryMemberVM createANewPantryMemberVM(String newMemberEmail, Long pantryId) {
        AddPantryMemberVM addPantryMemberVM = new AddPantryMemberVM();
        addPantryMemberVM.setPantryId(pantryId);
        addPantryMemberVM.setNewMemberEmail(newMemberEmail);
        return addPantryMemberVM;
    }

    public PantryUser addPantryMemberVMToPantryUser(AddPantryMemberVM addPantryMemberVM) {
        PantryUser pantryUser = new PantryUser();
        AcornUser user = new AcornUser();
        Pantry pantry = new Pantry();
        Optional<AcornUser> optionalAcornUser = acornUserService.findByEmail(addPantryMemberVM.getNewMemberEmail());
        Optional<Pantry> optionalPantry = pantryService.findById(addPantryMemberVM.getPantryId());
        if (optionalAcornUser.isPresent()) {
            user = optionalAcornUser.get();
        }
        if (optionalPantry.isPresent()) {
            pantry = optionalPantry.get();
        }
        pantryUser.setUser(user);
        pantryUser.setPantry(pantry);
        return pantryUser;
    }

    public static ProductDefinitionDTO productDefinitionToDTO(ProductDefinition productDefinition) {
        ProductDefinitionDTO productDefinitionDTO = new ProductDefinitionDTO();
        productDefinitionDTO.setId(productDefinition.getId());
        productDefinitionDTO.setName(productDefinition.getName());
        return productDefinitionDTO;
    }

    public static MakePantryAdminVM pantryUsertoMakePantryAdminVM(PantryUser pantryUser) {
        MakePantryAdminVM makePantryAdminVM = new MakePantryAdminVM();
        makePantryAdminVM.setPantryUserId(pantryUser.getId());
        makePantryAdminVM.setAcornUserId(pantryUser.getUser().getId());
        makePantryAdminVM.setIsAdministrator(pantryUser.isAdministrator());
        makePantryAdminVM.setUserName((pantryUser.getUser().getName()));
        makePantryAdminVM.setPantryName(pantryUser.getPantry().getName());
       return makePantryAdminVM;
    }

}
