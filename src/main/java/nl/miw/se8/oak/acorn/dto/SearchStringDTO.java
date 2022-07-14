package nl.miw.se8.oak.acorn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 13-7-2022
 */
@Getter @Setter
public class SearchStringDTO {

    private String keyword;

    public SearchStringDTO(String keywords) {
        this.keyword = keywords;
    }
}
