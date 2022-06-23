package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.viewmodel.UserEditView;
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
    protected String registerPost(@ModelAttribute("user") UserRegisterView registerUser,
                                  BindingResult result,
                                  Model model) {

        // TODO - Refactor magic strings (store in seperate class?)
        // This code is messy, find a better way
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Something went wrong, try again.");
            model.addAttribute("userRegisterView", new UserRegisterView());
            return "userRegister";
        } else if (!validEmail(registerUser.getEmail())) {
            model.addAttribute("errorMessage", "The email you entered is not valid.");
            model.addAttribute("userRegisterView", registerUser);
            return "userRegister";
        } else if (!validPassword(registerUser.getPassword())) {
            registerUser.resetPasswords();
            model.addAttribute("errorMessage", "The password you entered is not valid.");
            model.addAttribute("userRegisterView", registerUser);
            return "userRegister";
        } else if (!AcornUser.passwordsMatch(registerUser.getPassword(), registerUser.getPasswordCheck())) {
            registerUser.resetPasswords();
            model.addAttribute("errorMessage", "The passwords you entered do not match.");
            model.addAttribute("userRegisterView", registerUser);
            return "userRegister";
        } else if (emailAlreadyUsed(registerUser.getEmail())) {
            model.addAttribute("errorMessage", "That email is already in use.");
            model.addAttribute("userRegisterView", registerUser);
            return "userRegister";
        }

        AcornUser acornUser = new AcornUser(registerUser);
        acornUser.setPassword(passwordEncoder.encode(acornUser.getPassword()));

        userService.save(acornUser);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/profile")
    protected String profile(@AuthenticationPrincipal AcornUser acornUser, Model model) {
        UserEditView userEditView = new UserEditView(acornUser);
        model.addAttribute("userEditView", userEditView);
        return "/userProfile";
    }

    @PostMapping("/profile/edit")
    protected String editProfile(@ModelAttribute("userRegisterView") UserEditView userEditView,
                                 BindingResult result,
                                 Model model) {
        if (!result.hasErrors()) {
            Optional<AcornUser> optionalAcornUser = userService.findById(userEditView.getId());
            if (optionalAcornUser.isPresent()) {
                AcornUser acornUser = optionalAcornUser.get();

                if (userEditView.getEmail() != null) {
                    updateEmail(userEditView, acornUser, model);
                }

                if (userEditView.getOldPassword() != null) {
                    updatePassword(userEditView, acornUser);
                }

                userService.save(acornUser);
                updatePrincipal(acornUser);
            }
        }

        model.addAttribute(userEditView);
        return "/userProfile";
    }

    private void updateEmail(UserEditView userEditView, AcornUser user, Model model) {
        if (!userEditView.getEmail().equals(user.getEmail())) {
            if (!emailAlreadyUsed(userEditView.getEmail())) {
                user.setEmail(userEditView.getEmail());
                model.addAttribute("emailErrorMessage", "Succesfully updated your email!");
            } else {
                model.addAttribute("emailErrorMessage", "That email is already in use!");
            }
        }
    }

    private void updatePassword(UserEditView userEditView, AcornUser acornUser) {
        if (oldPasswordsMatch(userEditView, acornUser)) {
            if (newPasswordsMatch(userEditView)) {
                if (validPassword(userEditView.getNewPassword())) {
                    acornUser.setPassword(passwordEncoder.encode(userEditView.getNewPassword()));
                } else {
                    System.out.println("Your new password is not valid");
                }
            } else {
                System.out.println("The new passwords you entered do not match");
            }
        } else {
            System.out.println("Wrong password");
        }
    }

    public static boolean validEmail(String email) {
        return email.length() > AcornUser.MINIMAL_EMAIL_LENGTH;
    }

    private boolean emailAlreadyUsed(String email) {
        return userService.findByEmail(email).isPresent();
    }

    private boolean oldPasswordsMatch(UserEditView userEditView, AcornUser acornUser) {
        return passwordEncoder.matches(userEditView.getOldPassword(), acornUser.getPassword());
    }

    private boolean newPasswordsMatch(UserEditView userEditView) {
        return userEditView.getNewPassword().equals(userEditView.getNewPasswordCheck());
    }

    public static boolean validPassword(String password) {
        return password.length() > AcornUser.MINIMAL_PASSWORD_LENGTH;
    }

    // TODO - Is this good practice?
    private void updatePrincipal(AcornUser acornUser) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                acornUser,
                acornUser.getPassword(),
                acornUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
