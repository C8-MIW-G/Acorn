package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.PantryProductService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import nl.miw.se8.oak.acorn.viewmodel.PantryViewmodelIdName;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    public PantryController(PantryService pantryService,
                            PantryProductService pantryProductService,
                            PantryUserService pantryUserService) {
        this.pantryService = pantryService;
        this.pantryProductService = pantryProductService;
        this.pantryUserService = pantryUserService;
    }

    @GetMapping("/pantrySelection")
    protected String pantrySelection(Model model) {
        Mapper mapper = new Mapper();
        // gill a list with all pantries and create an empty arraylist for PantryViewmodels
        List<Pantry> pantries = pantryService.findAll();
        List<PantryViewmodelIdName> pantryViewmodels = new ArrayList<>();
        // Each pantry gets converted to a PantryViewodel (with attributes ID and Name) which is added to an Arraylist
        for (Pantry pantry: pantries) {
            pantryViewmodels.add(mapper.pantryToPantryViewmodelIdName(pantry));
        }
        // PantryViewmodels-ArrayList get loaded into the view.
        model.addAttribute("pantries", pantryViewmodels);
        return "pantrySelection";
    }

    @GetMapping("/pantry/{pantryId}/delete")
    protected String deletePantry(@PathVariable("pantryId") Long pantryId) {
        pantryService.deleteById(pantryId);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/pantry/create")                   // FIXME does this need o be a DTO/ViewModel? i made it but it seems obsolete here.
    protected String createPantry(Model model) {
        Mapper mapper = new Mapper();
        Pantry pantry = new Pantry();
        // the mapper here takes a pantry and return a pantryViewmodel (with attributes id and name) and loads it in the view.
        model.addAttribute("pantryToPantryEditViewmodel",mapper.pantryToPantryViewmodelIdName(pantry));
        return "pantryEdit.html";
    }

    @GetMapping("/pantry/{pantryId}/edit")
    protected String editPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        // the mapper here takes a pantry and return a pantryViewmodel (with attributes id and name) and loads it in the view.
        if (pantry.isPresent()) {
            Mapper mapper = new Mapper();
            model.addAttribute("pantryToPantryEditViewmodel", mapper.pantryToPantryViewmodelIdName(pantry.get()));
        }
        return "pantryEdit.html";
    }

    // the pantry (that is uplaoded to the db) in this method is actually a pantryViewmodel. However the database treats it as an actual Pantry.
    @PostMapping("/pantry/edit")
    protected String renamePantry(@Valid @ModelAttribute("pantry") Pantry pantry,
                                  BindingResult result,
                                  @AuthenticationPrincipal AcornUser acornUser) {
        // Check if a new pantry is created, or an existing pantry is edited.
        boolean newPantry = pantry.getId() == -1;

        if (!result.hasErrors()) {
            Pantry savedPantry = pantryService.save(pantry);

            // Sets current user as pantry administrator of newly created pantry
            if (newPantry) {
                PantryUser pantryUser = new PantryUser(acornUser, savedPantry, true);
                pantryUserService.save(pantryUser);
            }
        }
        return "redirect:/pantrySelection";
    }

}
