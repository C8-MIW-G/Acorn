package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.service.UserService;

public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
