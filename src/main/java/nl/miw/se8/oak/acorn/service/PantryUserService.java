package nl.miw.se8.oak.acorn.service;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.viewmodel.AddPantryMemberVM;

import java.util.List;
import java.util.Optional;

public interface PantryUserService {
    List<PantryUser> findAll();
    void save(PantryUser pantryUser);
    List<PantryUser> findPantryUserByUser(AcornUser user);
    List<PantryUser> findPantryUserByPantry(Pantry pantry);

    Optional<PantryUser> findById(Long pantryUserId);
    void deleteById(Long id);

    void saveByAddPantryMemberVM(AddPantryMemberVM newMember);
}
