package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.model.ProductDefinition;

import javax.validation.constraints.Size;

@Getter @Setter
public class ProductsDefinitionOverviewViewModel {

        private Long id;

        @Size(min = ProductDefinition.MIN_PRODUCT_DEFINITION_NAME_LENGTH,max = ProductDefinition.MAX_PRODUCT_DEFINITION_NAME_LENGTH, message = ProductDefinition.ERROR_NAME_TOO_SHORT)
        private String name;

        public ProductsDefinitionOverviewViewModel() {
        }
}


