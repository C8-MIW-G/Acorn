package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.Pantry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryRepository extends JpaRepository<Pantry, Long>{

    void deleteAllByIdAfter(Long id);
}
