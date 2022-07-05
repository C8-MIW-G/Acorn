package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 1-7-2022
 * OMSCHRIJVING
 */
public class SecurityController {

    public static AcornUser getCurrentUser() {
        return (AcornUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static void updatePrincipal(AcornUser acornUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                acornUser,
                acornUser.getPassword(),
                acornUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
