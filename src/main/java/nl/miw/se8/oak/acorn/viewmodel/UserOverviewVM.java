package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 30-6-2022
 */
@Getter @Setter
public class UserOverviewVM {

    private Long id;

    // TODO - refactor magic strings into finals
    @NotBlank(message = "This field cannot be empty.")
    @Size(max = 40, min = 5, message = "This field cannot contain less than 5 or more than 40 characters.")
    private String email;

    @NotBlank(message = "This field cannot be empty.")
    @Size(max = 20, min = 4, message = "This field cannot contain less than 4 or more than 20 characters.")
    private String name;

    public UserOverviewVM() {
    }

}
