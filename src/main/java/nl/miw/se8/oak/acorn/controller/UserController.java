package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.viewmodel.UserRegisterView;
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
        model.addAttribute("userRegisterView", new UserRegisterView());
        return "userRegister";
    }

    @PostMapping("/register")
    protected String registerPost(@ModelAttribute("user") UserRegisterView registerUser, BindingResult result, Model model) {

        // TODO - Refactor magic strings (store in seperate class?)
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Something went wrong, try again.");
            model.addAttribute("userRegisterView", new UserRegisterView());
            return "userRegister";
        } else if (!registerUser.validEmail()) {
            model.addAttribute("errorMessage", "The email you entered is not valid.");
            model.addAttribute("userRegisterView", new UserRegisterView());
            return "userRegister";
        } else if (!registerUser.validPassword()) {
            model.addAttribute("errorMessage", "The password you entered is not valid.");
            model.addAttribute("userRegisterView", new UserRegisterView());
            return "userRegister";
        } else if (!registerUser.passwordsMatch()) {
            model.addAttribute("errorMessage", "The passwords you entered do not match.");
            model.addAttribute("userRegisterView", new UserRegisterView());
            return "userRegister";
        } else if (emailAlreadyUsed(registerUser.getEmail())) {
            model.addAttribute("errorMessage", "That email is already in use.");
            model.addAttribute("userRegisterView", new UserRegisterView());
            return "userRegister";
        }

        AcornUser acornUser = new AcornUser(registerUser);
        acornUser.setPassword(passwordEncoder.encode(acornUser.getPassword()));

        userService.save(acornUser);
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

                if (returnedUser.getEmail() != null) {
                    user.setEmail(returnedUser.getEmail());
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

    private boolean emailAlreadyUsed(String email) {
        return userService.findByEmail(email).isPresent();
    }

}
