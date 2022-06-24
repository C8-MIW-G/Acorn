package nl.miw.se8.oak.acorn.controller;

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
    protected String homepage() {
        return "home";
    }



}
