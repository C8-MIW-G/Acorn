package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.dto.ProductDefinitionAjaxResponse;
import nl.miw.se8.oak.acorn.dto.ProductDefinitionDTO;
import nl.miw.se8.oak.acorn.dto.SearchStringDTO;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.service.ProductDefinitionService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getSearchResultViaAjax(@RequestBody SearchStringDTO search) {
        System.out.printf("Reached AJAX Controller: \"%s\"\n", search.getKeyword());

        ProductDefinitionAjaxResponse result = new ProductDefinitionAjaxResponse();

        List<ProductDefinition> allProducts = productDefinitionService.findByNameContains(search.getKeyword());
        List<ProductDefinitionDTO> productDTOS = new ArrayList<>();
        for (ProductDefinition product : allProducts) {
            System.out.println("\t" + product.getName());
            productDTOS.add(Mapper.productDefinitionToDTO(product));
        }

        result.setProductDefinitionDTOS(productDTOS);
        return ResponseEntity.ok(result);
    }

}
