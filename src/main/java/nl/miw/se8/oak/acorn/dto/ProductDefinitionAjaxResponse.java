package nl.miw.se8.oak.acorn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 13-7-2022
 */
@Getter @Setter
public class ProductDefinitionAjaxResponse {

    private List<ProductDefinitionDTO> productDefinitionDTOS;
}
