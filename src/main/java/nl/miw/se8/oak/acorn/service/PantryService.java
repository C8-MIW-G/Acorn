package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Pantry;
import java.util.List;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
public interface PantryService {
    List <Pantry> findAll();
    void deleteById(Long pantryId);
}
