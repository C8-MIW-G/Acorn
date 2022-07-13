package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.dto.ProductDefinitionAjaxResponse;
import nl.miw.se8.oak.acorn.dto.ProductDefinitionDTO;
import nl.miw.se8.oak.acorn.dto.SearchStringDTO;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/ajaxSearchProduct")
    public ResponseEntity<?> getSearchResultViaAjax(@RequestBody SearchStringDTO search, Errors errors) {

        ProductDefinitionAjaxResponse result = new ProductDefinitionAjaxResponse();

        List<ProductDefinition> productDefinitions = productDefinitionService.findByNameContains(search.getKeywords());
        List<ProductDefinitionDTO> productDefinitionDTOS = new ArrayList<>();
        for (ProductDefinition productDefinition : productDefinitions) {
            productDefinitionDTOS.add(Mapper.productDefinitionToDTO(productDefinition));
        }

        result.setProductDefinitionDTOS(productDefinitionDTOS);
        return ResponseEntity.ok(result);
    }

}
