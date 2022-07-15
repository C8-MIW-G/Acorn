package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.dto.ProductDefinitionAjaxResponse;
import nl.miw.se8.oak.acorn.dto.ProductDefinitionDTO;
import nl.miw.se8.oak.acorn.dto.SearchStringDTO;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 13-7-2022
 * Ajax functionality for looking up product definitions;
 */
@RestController
public class AjaxController {

    private final ProductDefinitionService productDefinitionService;

    public AjaxController(ProductDefinitionService productDefinitionService) {
        this.productDefinitionService = productDefinitionService;
    }

    @PostMapping("/ajaxSearchProduct")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody SearchStringDTO search, Errors errors) {
        // Check for errors
        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // Prepare ajax response
        ProductDefinitionAjaxResponse result = new ProductDefinitionAjaxResponse();

        // Fetch products from database
        List<ProductDefinition> products;
        if (search.getKeyword().length() != 0) {
            products = productDefinitionService.findByNameContains(search.getKeyword());
        } else {
            products = productDefinitionService.findAll();
        }

        // Convert to DTOs
        List<ProductDefinitionDTO> productDTOS = new ArrayList<>();
        for (ProductDefinition product : products) {
            productDTOS.add(Mapper.productDefinitionToDTO(product));
        }

        // Send it
        result.setProductDefinitionDTOS(productDTOS);
        return ResponseEntity.ok(result);
    }

}
