package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import nl.miw.se8.oak.acorn.model.AcornUser;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 30-6-2022
 */
@Getter
public class UserManagementViewModel {

    private Long id;
    private String email;

    public UserManagementViewModel(AcornUser acornUser) {
        this.id = acornUser.getId();
        this.email = acornUser.getEmail();
    }

}
