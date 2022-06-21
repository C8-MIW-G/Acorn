package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.Pantry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PantryRepository extends JpaRepository<Pantry, Long>{
    Optional<Pantry> findByName(String name);
}
