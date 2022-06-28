package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Controller
public class WelcomeController {

    public WelcomeController() { }

    @GetMapping("/")
    protected String homepage() {//@AuthenticationPrincipal AcornUser acornUser) {
//         if (acornUser != null) {
//            System.out.println("id: " + acornUser.getId());
//            System.out.println("name: " + acornUser.getName());
//            System.out.println("authorities: " + acornUser.getAuthorities());
//        }
        return "home";
    }



}
