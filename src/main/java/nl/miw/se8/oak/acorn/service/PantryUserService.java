package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.PantryUser;

import java.util.List;

public interface PantryUserService {
    List<PantryUser> findAll();
    void save(PantryUser pantryUser);
    List<PantryUser> findPantryUserByUser(AcornUser user);
    boolean currentUserHasAccessToPantry(Long pantryId);
}
