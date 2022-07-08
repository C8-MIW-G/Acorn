package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.model.Pantry;
import javax.validation.constraints.Size;

@Getter @Setter
public class PantryViewmodelIdName {

    private Long id;

    @Size(min = Pantry.MIN_PANTRY_NAME_LENGTH, max = Pantry.MAX_PANTRY_NAME_LENGTH, message = Pantry.ERROR_NAME_TOO_SHORT)
    private String name;

    public PantryViewmodelIdName() {
    }

}
