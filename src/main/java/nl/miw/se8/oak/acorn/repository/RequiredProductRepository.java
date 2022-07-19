package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.RequiredProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequiredProductRepository extends JpaRepository<RequiredProduct, Long> {
    List<RequiredProduct> findByPantryId(Long pantryId);
}
