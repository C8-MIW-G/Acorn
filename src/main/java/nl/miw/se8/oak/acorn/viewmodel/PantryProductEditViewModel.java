package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * @author Sylvia Kazakou
 *
 */

@Getter@Setter
public class PantryProductEditViewModel {

    public static final long DEFAULT_ID = -1L;
    private Long id;

    private String productDefinitionName;

    private LocalDate expirationDate;

    private Long pantryId;

    private Long productDefinitionId;
    public PantryProductEditViewModel() {
        this.id = DEFAULT_ID;
    }

}
