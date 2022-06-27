package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.model.PantryUser;
import nl.miw.se8.oak.acorn.service.PantryUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    public void userCreatedANewPantry(AcornUser acornUser, Pantry pantry) {
        System.out.println(acornUser.getId());
        System.out.println(acornUser.getName());

        PantryUser pantryCreator = new PantryUser(acornUser, pantry, true);
        pantryUserService.save(pantryCreator);
    }

}
