package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.repository.ProductDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<ProductDefinition> findById(Long productId) {
        return productDefinitionRepository.findById(productId);
    }

    @Override
    public void deleteById(Long id) {
        productDefinitionRepository.deleteById(id);
    }

    @Override
    public void save(ProductDefinition product) {
        productDefinitionRepository.save(product);
    }

    @Override
    public List<ProductDefinition> findByNameContaining(String string) {
        return productDefinitionRepository.findByNameContaining(string);
    }

}
