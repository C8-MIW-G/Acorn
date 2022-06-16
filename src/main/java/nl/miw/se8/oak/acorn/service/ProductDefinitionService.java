package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductDefinitionService {
    List<ProductDefinition> findAll();
    void deleteById(Long pantryId);

    Optional<ProductDefinition> findById(Long productDefinitionId);
}
