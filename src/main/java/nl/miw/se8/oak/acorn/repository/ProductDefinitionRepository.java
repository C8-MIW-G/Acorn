package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.ProductDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDefinitionRepository extends JpaRepository<ProductDefinition, Long> {
}
