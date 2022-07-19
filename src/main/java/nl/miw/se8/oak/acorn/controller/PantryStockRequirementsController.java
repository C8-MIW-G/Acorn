package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.dto.ProductDefinitionDTO;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.model.RequiredProduct;
import nl.miw.se8.oak.acorn.service.AuthorizationService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.service.RequiredProductService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.RequiredProductListVM;
import nl.miw.se8.oak.acorn.viewmodel.RequiredProductVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Controller
public class PantryStockRequirementsController {

    private final PantryService pantryService;
    private final RequiredProductService requiredProductService;
    private final AuthorizationService authorizationService;
    private final ProductDefinitionService productDefinitionService;

    public PantryStockRequirementsController(PantryService pantryService,
                                             RequiredProductService requiredProductService,
                                             AuthorizationService authorizationService,
                                             ProductDefinitionService productDefinitionService) {
        this.pantryService = pantryService;
        this.requiredProductService = requiredProductService;
        this.authorizationService = authorizationService;
        this.productDefinitionService = productDefinitionService;
    }

    @GetMapping("/pantry/{pantryId}/shopping-list")
    protected String fetchShoppingList(@PathVariable("pantryId") Long pantryId,
                                       Model model) {
        return "redirect:/pantry/{pantryId}";
    }

    @GetMapping("/pantry/{pantryId}/stock-requirements")
    protected String getStockRequirements(@PathVariable("pantryId") Long pantryId,
                                          Model model) {
        // Check authorization
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Look up pantry
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        // Fetch required products
        List<RequiredProduct> requiredProducts = requiredProductService.findByPantryId(pantryId);
        List<RequiredProductListVM> requiredProductListVMS = new ArrayList<>();
        for (RequiredProduct product : requiredProducts) {
            requiredProductListVMS.add(Mapper.requiredProductToListVM(product));
        }

        // Load new page with model
        model.addAttribute("products", requiredProductListVMS);
        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        return "requirementsPantryStock";
    }

    @GetMapping("/pantry/{pantryId}/stock-requirements/add")
    protected String addStockRequirements(@PathVariable("pantryId") Long pantryId,
                                          Model model) {
        // Check authorization
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Look up pantry
        Optional<Pantry> pantry = pantryService.findById(pantryId);
        if (pantry.isEmpty()) {
            return "redirect:/pantry/" + pantryId;
        }

        // Create required product VM for form
        RequiredProductVM requiredProductVM = Mapper.requiredProductToVM(new RequiredProduct());
        requiredProductVM.setPantryId(pantryId);

        // Fetch product definitions
        List<ProductDefinition> productDefinitions = productDefinitionService.findAll();
        List<ProductDefinitionDTO> productDefinitionDTOS = new ArrayList<>();
        for (ProductDefinition product : productDefinitions) {
            productDefinitionDTOS.add(Mapper.productDefinitionToDTO(product));
        }

        // Load new page with model
        model.addAttribute("pantry", Mapper.pantryToPantryEditVM(pantry.get()));
        model.addAttribute("product", requiredProductVM);
        model.addAttribute("productDefinitions", productDefinitionDTOS);
        return "requirementsPantryStockAdd";
    }

    @PostMapping("/pantry/{pantryId}/stock-requirements/add")
    protected String addStockRequirementPOST(@PathVariable("pantryId") Long pantryId,
                                             @ModelAttribute("product") RequiredProductVM requiredProductVM,
                                             BindingResult result,
                                             Model model) {
        // Check authorization
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // Check for errors
        } else if (result.hasErrors()) {
            return "redirect:/pantry/" + pantryId + "/stock-requirements";
        }

        // Store new requirement
        RequiredProduct requiredProduct = requiredProductService.VMToModel(requiredProductVM);
        if (requiredProduct != null) {
            if (requiredProductService.validAmount(requiredProduct.getAmount())) {
                requiredProductService.addToStack(requiredProduct);
            }
        }

        return "redirect:/pantry/" + pantryId + "/stock-requirements";
    }

    @GetMapping("/pantry/{pantryId}/stock-requirements/{productId}/delete")
    protected String removeStockRequirement(@PathVariable("pantryId") Long pantryId,
                                            @PathVariable("productId") Long requiredProductId) {
        // Check authorization
        if (!authorizationService.userCanEditPantry(pantryId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        requiredProductService.deleteById(requiredProductId);
        return "redirect:/pantry/" + pantryId + "/stock-requirements";
    }

}
