package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.RequiredProduct;
import nl.miw.se8.oak.acorn.repository.RequiredProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Service
public class RequiredProductServiceImplementation implements RequiredProductService {

    private final RequiredProductRepository requiredProductRepository;

    public RequiredProductServiceImplementation(RequiredProductRepository requiredProductRepository) {
        this.requiredProductRepository = requiredProductRepository;
    }

    @Override
    public RequiredProduct save(RequiredProduct requiredProduct) {
        return requiredProductRepository.save(requiredProduct);
    }

    @Override
    public List<RequiredProduct> findByPantryId(Long pantryId) {
        return requiredProductRepository.findByPantryId(pantryId);
    }

}
