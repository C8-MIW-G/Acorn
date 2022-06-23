package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;

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

    public void resetPasswords() {
        this.password = null;
        this.passwordCheck = null;
    }

}
