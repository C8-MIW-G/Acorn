package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.repository.PantryProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
@Service
public class PantryProductImplementation implements PantryProductService{

    PantryProductRepository pantryProductRepository;
    public PantryProductImplementation(PantryProductRepository pantryProductRepository) {
        this.pantryProductRepository = pantryProductRepository;
    }

    @Override
    public List<PantryProduct> findAllByPantryId(Long pantryId) {
        return pantryProductRepository.findAllByPantryId(pantryId);
    }
}
