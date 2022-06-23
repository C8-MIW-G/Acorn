package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.model.AcornUser;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 23-6-2022
 * OMSCHRIJVING
 */
@Getter @Setter
public class UserEditView {

    private Long id;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String newPasswordCheck;
    private String name;

    public UserEditView() {
    }

    public UserEditView(AcornUser acornUser) {
        this.id = acornUser.getId();
        this.email = acornUser.getEmail();
        this.name = acornUser.getName();
    }

    public void clearPasswords() {
        this.oldPassword = null;
        this.newPassword = null;
        this.newPasswordCheck = null;
    }
}
