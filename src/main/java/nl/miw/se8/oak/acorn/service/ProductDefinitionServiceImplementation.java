package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.repository.ProductDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 13-6-2022
 */

@Service
public class ProductDefinitionServiceImplementation implements ProductDefinitionService{

    ProductDefinitionRepository productDefinitionRepository;

    public ProductDefinitionServiceImplementation(ProductDefinitionRepository productDefinitionRepository) {
        this.productDefinitionRepository = productDefinitionRepository;
    }

    @Override
    public List<ProductDefinition> findAll() {
        return productDefinitionRepository.findAll();
    }
}
