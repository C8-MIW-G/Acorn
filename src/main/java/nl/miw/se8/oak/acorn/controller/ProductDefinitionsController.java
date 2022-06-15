package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/products")
    protected String productDefinitionsOverview(Model model) {
        List<ProductDefinition> productDefinitions = productDefinitionService.findAll();
        Collections.sort(productDefinitions);
        model.addAttribute("products", productDefinitions);
        return "productDefinitionsOverview";
    }

    @GetMapping("/products/{productId}")
    protected String productDefinitionsDetails(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDefinition> product = productDefinitionService.findById(productId);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productDefinitionsDetails";
        }
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}/delete")
    protected String deleteProductDefinition(@PathVariable("productId") Long productId) {
        productDefinitionService.deleteById(productId);
        return "redirect:/products";
    }

    @GetMapping("/products/create")
    protected String createProductDefinition(Model model) {
        model.addAttribute("product", new ProductDefinition());
        return "productDefinitionsCreate";
    }

    @GetMapping("/products/{productId}/edit")
    protected String editProductDefinition(@PathVariable("productId") Long productId, Model model) {
        Optional<ProductDefinition> productDefinition = productDefinitionService.findById(productId);
        if (productDefinition.isPresent()) {
            model.addAttribute("product", productDefinition.get());
            return "productDefinitionsCreate";
        }
        return "redirect:/products";
    }

    @PostMapping("/products/create")
    protected String submitProductDefinition(@ModelAttribute("productDefinition") ProductDefinition productDefinition, BindingResult result) {
        if (!result.hasErrors()) {
            productDefinitionService.save(productDefinition);
        }
        return "redirect:/products";
    }

    @GetMapping("/products/search")
    protected String searchProductDefinition(@RequestParam("searchString") String searchString, Model model) {
        List<ProductDefinition> searchResults = productDefinitionService.findByNameContaining(searchString);
        model.addAttribute("products", searchResults);
        System.out.println(Arrays.toString(searchResults.toArray()));
        return "productDefinitionsOverview";
    }

}
