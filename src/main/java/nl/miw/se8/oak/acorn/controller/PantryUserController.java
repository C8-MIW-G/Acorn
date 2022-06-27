package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.service.PantryUserService;
import org.springframework.stereotype.Controller;

/**
 * Author: Thijs van Blanken
 * Created on: 27-6-2022
 */
@Controller
public class PantryUserController {

    PantryUserService pantryUserService;

    public PantryUserController(PantryUserService pantryUserService) {
        this.pantryUserService = pantryUserService;
    }

}
