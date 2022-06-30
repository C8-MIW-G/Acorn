package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.model.Pantry;

import java.util.Optional;

@Getter @Setter
public class PantryEditModelView {

    private Long id;

    private String name;



    public PantryEditModelView() {
    }

    public PantryEditModelView(Optional<Pantry> pantry) {      // constructor that uses a pantry to create a pantr viewmodel
        this.id = pantry.getId();
        this.name = pantry.getName();
    }

    public OptionalPantryModelView(Optional<Pantry> pantry) {
        this.id =

    }
}
