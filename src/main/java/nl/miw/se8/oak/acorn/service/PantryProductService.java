package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.PantryProduct;

import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
public interface PantryProductService {
  List<PantryProduct> findAllByPantryId(Long pantryId);
  void deleteById(Long pantryProductId);
  Optional<PantryProduct> findById(Long pantryProductId);
}
