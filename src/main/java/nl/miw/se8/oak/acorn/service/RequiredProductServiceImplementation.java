package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.ProductDefinition;
import nl.miw.se8.oak.acorn.model.RequiredProduct;
import nl.miw.se8.oak.acorn.repository.RequiredProductRepository;
import nl.miw.se8.oak.acorn.viewmodel.RequiredProductVM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Service
public class RequiredProductServiceImplementation implements RequiredProductService {

    private final RequiredProductRepository requiredProductRepository;
    private final PantryService pantryService;
    private final ProductDefinitionService productDefinitionService;

    public RequiredProductServiceImplementation(RequiredProductRepository requiredProductRepository,
                                                PantryService pantryService,
                                                ProductDefinitionService productDefinitionService) {
        this.requiredProductRepository = requiredProductRepository;
        this.pantryService = pantryService;
        this.productDefinitionService = productDefinitionService;
    }

    @Override
    public RequiredProduct save(RequiredProduct requiredProduct) {
        return requiredProductRepository.save(requiredProduct);
    }

    @Override
    public List<RequiredProduct> findByPantryId(Long pantryId) {
        return requiredProductRepository.findByPantryId(pantryId);
    }

    @Override
    public RequiredProduct VMToModel(RequiredProductVM requiredProductVM) {
        RequiredProduct requiredProduct = new RequiredProduct();

        Optional<ProductDefinition> productDefinition = productDefinitionService.findById(requiredProductVM.getProductDefinitionId());
        Optional<Pantry> pantry = pantryService.findById(requiredProductVM.getPantryId());
        if (productDefinition.isEmpty() || pantry.isEmpty()) {
            return null;
        }

        requiredProduct.setId(requiredProductVM.getId());
        requiredProduct.setAmount(requiredProductVM.getAmount());
        requiredProduct.setProductDefinition(productDefinition.get());
        requiredProduct.setPantry(pantry.get());
        return requiredProduct;
    }

    @Override
    public void deleteById(Long requiredProductId) {
        requiredProductRepository.deleteById(requiredProductId);
    }

    @Override
    public boolean validAmount(int amount) {
        if (amount > 0 && amount <= RequiredProduct.MAX_AMOUNT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addToStack(RequiredProduct requiredProduct) {
        Optional<RequiredProduct> dbRequiredProduct = requiredProductRepository.findByPantryIdAndProductDefinitionId(
                requiredProduct.getPantry().getId(), requiredProduct.getProductDefinition().getId());

        if (dbRequiredProduct.isPresent()) {
            dbRequiredProduct.get().addToAmount(requiredProduct.getAmount());
            requiredProductRepository.save(dbRequiredProduct.get());
        } else {
            requiredProductRepository.save(requiredProduct);
        }
    }

    @Override
    public Optional<RequiredProduct> findById(Long id) {
        return requiredProductRepository.findById(id);
    }

}
