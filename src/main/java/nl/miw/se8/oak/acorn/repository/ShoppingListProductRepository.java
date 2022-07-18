package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.ShoppingListProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListProductRepository extends JpaRepository<ShoppingListProduct, Long> {
    List<ShoppingListProduct> findByPantryId(Long pantryId);
}
