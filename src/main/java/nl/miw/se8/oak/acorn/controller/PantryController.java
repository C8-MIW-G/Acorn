package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.*;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
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
        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        pantryService.deleteById(pantryId);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/pantry/create")                   // FIXME does this need o be a DTO/ViewModel? i made it but it seems obsolete here.
    protected String createPantry(Model model) {
        Pantry pantry = new Pantry();
        model.addAttribute("pantryToPantryEditViewmodel",Mapper.pantryToPantryEditVM(pantry));
        return "pantryEdit.html";
    }

    @GetMapping("/pantry/{pantryId}/edit")
    protected String editPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            model.addAttribute("pantryToPantryEditViewmodel", Mapper.pantryToPantryEditVM(pantry.get()));
        }
        return "pantryEdit.html";
    }

    @PostMapping("/pantry/edit")
    protected String renamePantry(@Valid @ModelAttribute("pantry") Pantry pantry,
                                  BindingResult result,
                                  @AuthenticationPrincipal AcornUser acornUser) {
        if (!result.hasErrors()) {
            // Check if a new pantry is created, or an existing pantry is edited.
            if (pantry.getId() == -1) {
                Pantry savedPantry = pantryService.save(pantry);
                PantryUser pantryUser = new PantryUser(acornUser, savedPantry, true);
                pantryUserService.save(pantryUser);
            } else if (!authorizationService.userCanAccessPantry(pantry.getId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        }
        return "redirect:/pantrySelection";
    }

}
