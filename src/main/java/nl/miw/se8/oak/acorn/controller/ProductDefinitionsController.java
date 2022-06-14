package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("products", productDefinitions);
        return "productDefinitionsOverview";
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

}
