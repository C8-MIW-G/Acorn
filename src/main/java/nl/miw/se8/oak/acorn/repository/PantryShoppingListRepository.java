package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.PantryShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PantryShoppingListRepository extends JpaRepository<PantryShoppingList, Long> {
    Optional<PantryShoppingList> findByPantryId(Long pantryId);
}
