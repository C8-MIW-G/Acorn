package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.repository.PantryProductRepository;
import nl.miw.se8.oak.acorn.repository.ProductDefinitionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
@Service
public class PantryProductImplementation implements PantryProductService{

    PantryProductRepository pantryProductRepository;

    public PantryProductImplementation(PantryProductRepository pantryProductRepository, ProductDefinitionRepository productDefinitionRepository) {
        this.pantryProductRepository = pantryProductRepository;
    }

    @Override
    public List<PantryProduct> findAll() {
        return pantryProductRepository.findAll();
    }

    @Override
    public List<PantryProduct> findAllByPantryId(Long pantryId) {
        return pantryProductRepository.findAllByPantryId(pantryId);
    }
    @Override
    public void deleteById(Long pantryProductId) {
        pantryProductRepository.deleteById(pantryProductId);
    }

    @Override
    public Optional<PantryProduct> findById(Long pantryProductId) {
        return pantryProductRepository.findById(pantryProductId);
    }

    public void save(PantryProduct pantryProduct) {
        pantryProductRepository.save(pantryProduct);
    }
}
