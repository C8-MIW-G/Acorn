package nl.miw.se8.oak.acorn.viewmodel;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: Thijs van Blanken
 * Created on: 19-7-2022
 */
@Getter @Setter
public class RequiredProductListVM {
    private Long id;
    private Long pantryId;
    private String productDefinitionName;
    private int amount;
}
