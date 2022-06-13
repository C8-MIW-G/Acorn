package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductDefinitionService {
    List<ProductDefinition> findAll();
}
