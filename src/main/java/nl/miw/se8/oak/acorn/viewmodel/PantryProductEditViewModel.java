package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se8.oak.acorn.model.PantryProduct;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author Sylvia Kazakou
 *
 */

@Getter @Setter
public class PantryProductEditViewModel {

    private Long id;
    private Long pantryId;
    private Long productDefinitionId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expirationDate;

    public PantryProductEditViewModel() { }

}
