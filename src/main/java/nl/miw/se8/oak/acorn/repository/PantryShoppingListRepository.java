package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.PantryShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryShoppingListRepository extends JpaRepository<PantryShoppingList, Long> {
}
