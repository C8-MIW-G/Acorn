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
public class UserRegisterView {

    private String email;
    private String password;
    private String passwordCheck;

    public UserRegisterView() {

    }

    // TODO - expand validating email
    public boolean validEmail() {
        return email.length() > AcornUser.MINIMAL_EMAIL_LENGTH;
    }

    public boolean validPassword() {
        return password.length() > AcornUser.MINIMAL_PASSWORD_LENGTH;
    }

    public boolean passwordsMatch() {
        return password.equals(passwordCheck);
    }

}
