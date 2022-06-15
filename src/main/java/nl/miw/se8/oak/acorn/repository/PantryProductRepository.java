package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.PantryProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryProductRepository extends JpaRepository<PantryProduct, Long> {
    List<PantryProduct> findAllByPantryId (Long pantryId);

}


