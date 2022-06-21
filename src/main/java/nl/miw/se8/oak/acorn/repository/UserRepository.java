package nl.miw.se8.oak.acorn.repository;

import nl.miw.se8.oak.acorn.model.AcornUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AcornUser, Long> {
}
