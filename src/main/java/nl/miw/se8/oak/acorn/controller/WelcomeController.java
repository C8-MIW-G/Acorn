package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Role;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Author: Team Oak
 * Created on: 9-6-2022
 */
@Controller
public class WelcomeController {

    public WelcomeController() { }

    @GetMapping("/")
    protected String homepage(@AuthenticationPrincipal AcornUser acornUser) {
        if (acornUser != null) {
            System.out.println("Logged in user:");
            System.out.printf("\t[id: %d]\n", acornUser.getId());
            System.out.printf("\t[name: %s]\n", acornUser.getName());
            System.out.printf("\t[email: %s]\n", acornUser.getEmail());
            System.out.printf("\t[password: %s]\n", acornUser.getPassword());
            for (Role role : acornUser.getRoles()) {
                System.out.printf("\t[role: %s]\n", role.getName());
            }
            System.out.printf("\t[Authorities: %s]\n", acornUser.getAuthorities());
        }

        return "home";
    }

}
