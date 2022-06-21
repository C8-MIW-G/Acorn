package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryProduct;

import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
public interface PantryService {
    List <Pantry> findAll();
    void deleteById(Long pantryId);
    Optional<Pantry> findById(Long pantryId);
    void save(Pantry pantry);
    Optional<Pantry> findByName(String name);

}
