package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.model.Pantry;

@Getter @Setter
public class PantryEditViewmodel {


    private Long id;

    private String name;



    public PantryEditViewmodel() {
    }

    public PantryEditViewmodel(Pantry pantry) {
        this.id = pantry.getId();
        this.name = pantry.getName();
    }
}
