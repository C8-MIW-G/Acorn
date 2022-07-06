package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Thijs van Blanken
 * Created on: 5-7-2022
 */
@Getter @Setter
public class UserRegisterVM {

    private String email;
    private String name;
    private String password;
    private String passwordCheck;

    public UserRegisterVM() {
    }

}