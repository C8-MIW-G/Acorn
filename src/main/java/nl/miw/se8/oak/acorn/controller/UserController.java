package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.User;
import nl.miw.se8.oak.acorn.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    UserService userService;
    PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    protected String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "userRegister";
    }

    @PostMapping("/register")
    protected String registerPost(@ModelAttribute("user") User user, BindingResult result) {

        // TODO - Clean up
        if (result.hasErrors()) {
            System.out.println("Result has errors");
            return "userRegister";
        } else if (user.getUsername().length() < User.MINIMAL_USERNAME_LENGTH) {
            System.out.println("Username too short.");
            return "userRegister";
        } else if (user.getPassword().length() < User.MINIMAL_PASSWORD_LENGTH) {
            System.out.println("Password too short.");
            return "userRegister";
        } else if (usernameInDatabase(user.getUsername())) {
            System.out.println("Username already in use");
            return "userRegister";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDisplayName(user.getUsername());
        userService.save(user);

        // TODO redirect to users' pantry overview
        return "redirect:/";
    }

    private boolean usernameInDatabase(String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.isPresent();
    }

}
