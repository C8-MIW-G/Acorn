package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.*;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.PantryProductEditViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Sylvia Kazakou
 * Deal with contents of a pantry
 */
@Controller
public class PantryProductController {

    PantryProductService pantryProductService;
    ProductDefinitionService productDefinitionService;
    PantryService pantryService;
    PantryUserService pantryUserService;
    AuthorizationService authorizationService;

    public PantryProductController(PantryProductService pantryProductService,
                                   ProductDefinitionService productDefinitionService,
                                   PantryService pantryService,
                                   PantryUserService pantryUserService,
                                   AuthorizationService authorizationService) {
        this.pantryProductService = pantryProductService;
        this.productDefinitionService = productDefinitionService;
        this.pantryService = pantryService;
        this.pantryUserService = pantryUserService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/pantry/{pantryId}")
    protected String pantryContents(@PathVariable("pantryId") Long pantryId, Model model) {
        List<PantryProduct> pantryProducts = pantryProductService.findAllByPantryId(pantryId);
        Collections.sort(pantryProducts);
        model.addAttribute("pantryProducts", pantryProducts);

        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isPresent()) {
            if (authorizationService.userCanAccessPantry(pantryId)) {
                model.addAttribute("pantryName", pantry.get().getName());
                model.addAttribute("memberCount", pantry.get().getPantryUsers().size());
                model.addAttribute("userCanEditPantry", authorizationService.userCanEditPantry(pantryId));
                return "pantryContents";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/pantryProduct/{pantryProductId}/delete")
    protected String deletePantryProduct(@PathVariable("pantryProductId") Long pantryProductId) {
        Optional<PantryProduct> pantryProduct = pantryProductService.findById(pantryProductId);
        Long pantryId = null;
        if (pantryProduct.isPresent()) {
            pantryId = pantryProduct.get().getPantry().getId();
        }
        pantryProductService.deleteById(pantryProductId);

        if (pantryId != null) {
            return "redirect:/pantry/" + pantryId;
        } else {
            return "redirect:/pantrySelection";
        }
    }

    @GetMapping("/pantry/{pantryId}/add")
    protected String addNewItemInPantry(@PathVariable("pantryId") Long pantryId, Model model) {
        // Add viewmodel to model
        PantryProductEditViewModel pantryProductVM = Mapper.pantryProductToPantryProductEditViewModel(new PantryProduct());
        pantryProductVM.setPantryId(pantryId);
        model.addAttribute("pantryProduct", pantryProductVM);

        // Add list of product definitions to model
        List<ProductDefinition> productDefinitions = productDefinitionService.findAll();
        model.addAttribute("productDefinitions", productDefinitions);

        // Add pantry name to model
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        pantry.ifPresent(value -> model.addAttribute("pantryName", value.getName()));

        // Load the page using the model
        return "pantryProductAdd";
    }

    @GetMapping("/pantry/{pantryId}/edit/{pantryProductId}")
    protected String editItemInPantry(@PathVariable("pantryId") Long pantryId,
                                      @PathVariable("pantryProductId") Long pantryProductId,
                                      Model model) {
        Optional<PantryProduct> pantryProduct = pantryProductService.findById(pantryProductId);
        if (pantryProduct.isPresent()) {
            PantryProductEditViewModel pantryProductVM = Mapper.pantryProductToPantryProductEditViewModel(pantryProduct.get());
            model.addAttribute("pantryProduct", pantryProductVM);
            return "pantryProductEdit";
        }
        return "redirect:/pantry/" + pantryId;
    }

    @PostMapping("/pantry/{pantryId}/add")
    protected String savePantryProduct(@PathVariable("pantryId") Long pantryId,
                                       @ModelAttribute("pantryProduct") PantryProductEditViewModel pantryProductVM,
                                       @ModelAttribute("amount") int amount,
                                       BindingResult result) {
        if (!authorizationService.userCanAccessPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (result.hasErrors() || amount < PantryProduct.MIN_AMOUNT || amount > PantryProduct.MAX_AMOUNT) {
            return "redirect:/pantry/" + pantryId + "/add";
        }

        PantryProduct pantryProduct = pantryProductService.pantryProductEditVMToPantryProduct(pantryProductVM);
        for (int i = 0; i < amount; i++) {
            pantryProductService.save(pantryProduct);
        }

        return "redirect:/pantry/" + pantryProductVM.getPantryId();
    }

    @PostMapping("pantry/{pantryId}/edit")
    protected String editPantryProduct(@ModelAttribute("pantryProduct") PantryProductEditViewModel pantryProductVM, BindingResult result) {
        PantryProduct pantryProduct = pantryProductService.findById(pantryProductVM.getId()).get();

        if (!result.hasErrors() && pantryProductVM.getExpirationDate() != null) {
           pantryProduct.setExpirationDate(pantryProductVM.getExpirationDate());
       } else {
            pantryProduct.setExpirationDate(null);
        }

        pantryProductService.save(pantryProduct);
        return "redirect:/pantry/" + pantryProductVM.getPantryId();
    }

}

