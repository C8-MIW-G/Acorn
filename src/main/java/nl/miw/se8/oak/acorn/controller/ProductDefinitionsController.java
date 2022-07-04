package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.ProductsViewModel;
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
        List<ProductsViewModel> productsViewModels = new ArrayList<>();

        for (ProductDefinition productdefinition: productDefinitions) {
            productsViewModels.add(mapper.productDefToProductViewModel(productdefinition));
        }
        model.addAttribute("products", productsViewModels);
        return "productDefinitionsOverview";
    }

    @GetMapping("/products/{productId}")            // FIXME method returns error
    protected String productDefinitionsDetails(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDefinition> product = productDefinitionService.findById(productId);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productDefinitionsDetails";
        }
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}/delete")         // no DTO/viewmodel nodig
    protected String deleteProductDefinition(@PathVariable("productId") Long productId) {
        productDefinitionService.deleteById(productId);
        return "redirect:/products";
    }

    @GetMapping("/products/create")                     // DTO/Viewmodel nodig? vragen aan Joost
    protected String createProductDefinition(Model model) {
        model.addAttribute("product", new ProductDefinition());
        return "productDefinitionsCreate";
    }

    @GetMapping("/products/{productId}/edit")               // sowieso DTO nodig, dunkt mij
    protected String editProductDefinition(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDefinition> productDefinition = productDefinitionService.findById(productId);
        if (productDefinition.isPresent()) {
            model.addAttribute("product", productDefinition.get());
            return "productDefinitionsCreate";
        }
        return "redirect:/products";
    }

    @PostMapping("/products/create")                 // DTO/ViewModel nodig?
    protected String submitProductDefinition(@ModelAttribute("productDefinition") ProductDefinition productDefinition, BindingResult result) {
        if (!result.hasErrors()) {
            productDefinitionService.save(productDefinition);
        }
        return "redirect:/products";
    }

}
