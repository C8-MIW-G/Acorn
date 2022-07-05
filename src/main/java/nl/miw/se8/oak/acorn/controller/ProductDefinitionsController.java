package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.ProductsDefinitionOverviewViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Author: Thijs van Blanken
 * Created on: 14-6-2022
 * Contains all service functionality that has to do with the ProductDefinitions model
 */
@Controller
@RequestMapping("/admin")
public class ProductDefinitionsController {

    ProductDefinitionService productDefinitionService;

    public ProductDefinitionsController(ProductDefinitionService productDefinitionService) {
        this.productDefinitionService = productDefinitionService;
    }

    @GetMapping("/products")                                        // incorporated viewmodel
    protected String productDefinitionsOverview(Model model) {
        Mapper mapper = new Mapper();
        List<ProductDefinition> productDefinitions = productDefinitionService.findAll();
        Collections.sort(productDefinitions);
        List<ProductsDefinitionOverviewViewModel> productsDefinitionOverviewViewModels = new ArrayList<>();

        for (ProductDefinition productdefinition: productDefinitions) {
            productsDefinitionOverviewViewModels.add(mapper.productDefToProductDefVM(productdefinition));
        }
        model.addAttribute("products", productsDefinitionOverviewViewModels);
        return "productDefinitionsOverview";
    }

    @GetMapping("/products/{productId}/delete")
    protected String deleteProductDefinition(@PathVariable("productId") Long productId) {
        productDefinitionService.deleteById(productId);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/create")                     // incorporated viewmodel
    protected String createProductDefinition(Model model) {
        model.addAttribute("product", new ProductsDefinitionOverviewViewModel());
        return "productDefinitionsCreate";
    }

    @GetMapping("/products/{productId}/edit")               // incorporated viewModel
    protected String editProductDefinition(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDefinition> productDefinition = productDefinitionService.findById(productId);
        Mapper mapper = new Mapper();

        if (productDefinition.isPresent()) {
            model.addAttribute("product", mapper.productDefToProductDefVM(productDefinition.get()));
            return "productDefinitionsCreate";
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/products/create")                 // DTO/ViewModel nodig?
    protected String submitProductDefinition(@ModelAttribute("productDefinition") ProductDefinition productDefinition, BindingResult result) {
        if (!result.hasErrors()) {
            productDefinitionService.save(productDefinition);
        }
        return "redirect:/admin/products";
    }

}
