package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Thijs van Blanken
 * Created on: 18-7-2022
 */
@Getter @Setter
public class RequiredProductVM {
    private Long id;
    private Long pantryId;
    private Long productDefinitionId;
    private int amount;
}
