package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.*;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.PantryProductEditViewModel;
import nl.miw.se8.oak.acorn.viewmodel.PantryViewmodelIdName;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Controller
public class PantryController {

    PantryService pantryService;
    PantryProductService pantryProductService;
    PantryUserService pantryUserService;
    AcornUserService acornUserService;
    AuthorizationService authorizationService;

    public PantryController(PantryService pantryService,
                            PantryProductService pantryProductService,
                            PantryUserService pantryUserService,
                            AcornUserService acornUserService,
                            AuthorizationService authorizationService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
        this.pantryUserService = pantryUserService;
        this.acornUserService = acornUserService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/pantrySelection")
    protected String pantrySelection(Model model) {
        AcornUser acornUser = SecurityController.getCurrentUser();
        List<PantryUser> pantryUsers = pantryUserService.findPantryUserByUser(acornUser);
        List<PantryViewmodelIdName> pantryViewModels = new ArrayList<>();

        for (PantryUser pantryUser : pantryUsers) {
            Optional<Pantry> optionalPantry = pantryService.findById(pantryUser.getPantry().getId());
            if (optionalPantry.isPresent()) {
                pantryViewModels.add(Mapper.pantryToPantryEditVM(optionalPantry.get()));
            }
        }

        model.addAttribute("pantries", pantryViewModels);
        return "pantrySelection";
    }

    @GetMapping("/pantry/{pantryId}/delete")
    protected String deletePantry(@PathVariable("pantryId") Long pantryId) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        pantryService.deleteById(pantryId);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/pantry/create")
    protected String createPantry(Model model) {
        Pantry pantry = new Pantry();
        model.addAttribute("pantryVM",Mapper.pantryToPantryEditVM(pantry));
        return "pantryEdit";
    }

    @GetMapping("/pantry/{pantryId}/edit")
    protected String editPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            model.addAttribute("pantryVM", Mapper.pantryToPantryEditVM(pantry.get()));
        }
        return "pantryEdit";
    }

    @PostMapping("/pantry/edit")
    protected String renamePantry(@Valid @ModelAttribute("pantryVM") PantryViewmodelIdName pantryVM,
                                  BindingResult result,
                                  @AuthenticationPrincipal AcornUser acornUser) {
        boolean newPantry = pantryVM.getId() == -1;

        if (!newPantry && !authorizationService.userCanEditPantry(pantryVM.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (result.hasErrors()) {
            return "pantryEdit";
        }
        
        Pantry savedPantry = pantryService.save(Mapper.pantryEditVMToPantry(pantryVM));

        // Set current user as pantry administrator for new pantry
        if (newPantry) {
            PantryUser pantryUser = new PantryUser(acornUser, savedPantry, true);
            pantryUserService.save(pantryUser);
        }

        return "redirect:/pantrySelection";
    }

}
