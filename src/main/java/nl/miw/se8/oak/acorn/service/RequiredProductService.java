package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.RequiredProduct;
import nl.miw.se8.oak.acorn.viewmodel.RequiredProductVM;

import java.util.List;
import java.util.Optional;

public interface RequiredProductService {
    RequiredProduct save(RequiredProduct requiredProduct);
    List<RequiredProduct> findByPantryId(Long pantryId);
    RequiredProduct VMToModel(RequiredProductVM requiredProductVM);
    void deleteById(Long requiredProductId);
    boolean validAmount(int amount);
    void addToStack(RequiredProduct requiredProduct);
    Optional<RequiredProduct> findById(Long id);
}
