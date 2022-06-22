package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
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

    AcornUserService userService;
    PasswordEncoder passwordEncoder;

    public UserController(AcornUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    protected String registerGet(Model model) {
        model.addAttribute("user", new AcornUser());
        return "userRegister";
    }

    @PostMapping("/register")
    protected String registerPost(@ModelAttribute("user") AcornUser user, BindingResult result, Model model) {

        // TODO - Refactor magic strings (store in seperate class?)
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Something went wrong, try again.");
            return "userRegister";
        } else if (user.getUsername().length() < AcornUser.MINIMAL_USERNAME_LENGTH) {
            model.addAttribute("errorMessage", "The username you chose is too short.");
            return "userRegister";
        } else if (user.getPassword().length() < AcornUser.MINIMAL_PASSWORD_LENGTH) {
            model.addAttribute("errorMessage", "The password you entered is too short.");
            return "userRegister";
        } else if (usernameInDatabase(user.getUsername())) {
            model.addAttribute("errorMessage", "That username is already in use.");
            return "userRegister";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDisplayName(user.getUsername());
        userService.save(user);

        return "redirect:/pantrySelection";
    }

    private boolean usernameInDatabase(String username) {
        Optional<AcornUser> user = userService.findByUsername(username);
        return user.isPresent();
    }

    @Controller
    class LoginController {
        @GetMapping("/login")
        String login() {
            return "login";
        }
    }
//
//    @Controller
//    class LogoutController {
        @GetMapping("/logoutForm")
        String logout() {
            return "logoutForm";
        }
    }

    @Controller
    class LogoutController {
        @PostMapping("/logout")
        String logout() {
            return "logoutForm";
        }
    }


