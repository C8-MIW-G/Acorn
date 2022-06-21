package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.User;
import nl.miw.se8.oak.acorn.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    protected String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "userRegister";
    }

    @PostMapping("/register")
    protected String registerPost(@ModelAttribute("user") User user) {
        user.setDisplayName(user.getUsername());
        userService.save(user);
        // TODO redirect to users' pantry overview
        return "redirect:/";
    }
}
