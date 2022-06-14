package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.ProductDefinition;

import java.util.List;
import java.util.Optional;

public interface ProductDefinitionService {
    List<ProductDefinition> findAll();
    void deleteById(Long productId);
    void save(ProductDefinition product);
}
