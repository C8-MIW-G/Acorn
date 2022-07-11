package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.repository.PantryProductRepository;
import nl.miw.se8.oak.acorn.repository.ProductDefinitionRepository;
import nl.miw.se8.oak.acorn.viewmodel.PantryProductEditViewModel;
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
    PantryService pantryService;
    ProductDefinitionService productDefinitionService;

    public PantryProductImplementation(PantryProductRepository pantryProductRepository,
                                       PantryService pantryService,
                                       ProductDefinitionService productDefinitionService) {
        this.pantryProductRepository = pantryProductRepository;
        this.pantryService = pantryService;
        this.productDefinitionService = productDefinitionService;
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

    // This is basically a mapper method, but it relies on different services that can't be accessed in the Mapper class.
    @Override
    public PantryProduct pantryProductEditVMToPantryProduct(PantryProductEditViewModel pantryProductEditViewModel) {
        PantryProduct pantryProduct = new PantryProduct();
        pantryProduct.setPantry(pantryService.findById(pantryProductEditViewModel.getPantryId()).get());
        pantryProduct.setProductDefinition(productDefinitionService.findById(pantryProductEditViewModel.getProductDefinitionId()).get());
        pantryProduct.setExpirationDate(pantryProductEditViewModel.getExpirationDate());
        return pantryProduct;
    }
}
