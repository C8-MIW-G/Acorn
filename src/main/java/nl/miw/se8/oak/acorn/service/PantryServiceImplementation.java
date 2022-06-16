package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.repository.PantryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Author: Team Oak
 * Created on: 10-6-2022
 */
@Service
public class PantryServiceImplementation implements PantryService {

    PantryRepository pantryRepository;

    public PantryServiceImplementation(PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    @Override
    public List<Pantry> findAll() {
        return pantryRepository.findAll();
    }

    @Override
    public void deleteById(Long pantryId) {
        pantryRepository.deleteById(pantryId);
    }

    @Override
    public Optional<Pantry> findById(Long pantryId) {
        return pantryRepository.findById(pantryId);
    }

    @Override
    public void save(Pantry pantry) {
        pantryRepository.save(pantry);
    }

}
