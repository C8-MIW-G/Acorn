package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    protected String registerPost(@ModelAttribute("user") AcornUser registerUser, BindingResult result, Model model) {

        // TODO - Refactor magic strings (store in seperate class?)
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Something went wrong, try again.");
            return "userRegister";
        } else if (usernameInvalid(registerUser)) {
            model.addAttribute("errorMessage", "The username you chose is too short.");
            return "userRegister";
        } else if (passwordInvalid(registerUser)) {
            model.addAttribute("errorMessage", "The password you entered is too short.");
            return "userRegister";
        } else if (usernameInDatabase(registerUser)) {
            model.addAttribute("errorMessage", "That username is already in use.");
            return "userRegister";
        }

        registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        registerUser.setDisplayName(registerUser.getUsername());
        userService.save(registerUser);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/profile")
    protected String currentUser(@AuthenticationPrincipal AcornUser acornUser, Model model) {
        model.addAttribute("user", acornUser);
        return "/userProfile";
    }

    @PostMapping("/profile/edit")
    protected String editDisplayName(@ModelAttribute("user") AcornUser returnedUser, BindingResult result) {
        if (!result.hasErrors()) {
            Optional<AcornUser> databaseUser = userService.findById(returnedUser.getId());
            if (databaseUser.isPresent()) {
                AcornUser user = databaseUser.get();

                if (returnedUser.getDisplayName() != null) {
                    user.setDisplayName(returnedUser.getDisplayName());
                }
                if (returnedUser.getPassword() != null) {
                    // TODO - Ask user for old password as well and compare
                    user.setPassword(passwordEncoder.encode(returnedUser.getPassword()));
                }

                userService.save(user);
                updatePrincipal(user);
            }
        }
        return "redirect:/profile";
    }

    // TODO - Is this good practice?
    private void updatePrincipal(AcornUser acornUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                acornUser,
                acornUser.getPassword(),
                acornUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean usernameInvalid(AcornUser user) {
        return user.getUsername().length() < AcornUser.MINIMAL_USERNAME_LENGTH;
    }

    private boolean passwordInvalid(AcornUser user) {
        return user.getPassword().length() < AcornUser.MINIMAL_PASSWORD_LENGTH;
    }

    private boolean usernameInDatabase(AcornUser user) {
        return userService.findByUsername(user.getUsername()).isPresent();
    }

}
