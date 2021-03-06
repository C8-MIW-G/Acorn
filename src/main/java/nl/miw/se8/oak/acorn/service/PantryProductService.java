package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import nl.miw.se8.oak.acorn.viewmodel.PantryProductEditViewModel;

import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
public interface PantryProductService {
  List<PantryProduct> findAll();
  List<PantryProduct> findAllByPantryId(Long pantryId);
  void deleteById(Long pantryProductId);
  Optional<PantryProduct> findById(Long pantryProductId);
  void save(PantryProduct pantryProduct);
  PantryProduct pantryProductEditVMToPantryProduct(PantryProductEditViewModel pantryProductEditViewModel);
}
