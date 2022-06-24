package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.viewmodel.UserEditView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    public static final String ERROR_RESULT_HAS_ERRORS = "Something went wrong, try again.";
    public static final String ERROR_EMAIL_INVALID = "The e-mailadres you entered is not valid.";
    public static final String ERROR_EMAIL_IN_USE = "That e-mailadres is already in use.";
    public static final String ERROR_PASSWORD_INCORRECT = "You entered an incorrect password";
    public static final String ERROR_PASSWORD_INVALID = "The password you entered is not valid.";
    public static final String ERROR_PASSWORDS_NO_MATCH = "The passwords you entered are not an exact match.";
    public static final String INFO_EMAIL_UPDATE_SUCCESS = "Successfully updated your email!";
    public static final String INFO_PASSWORD_UPDATE_SUCCESS = "Successfully changed your password!";

    AcornUserService userService;
    PasswordEncoder passwordEncoder;

    public UserController(AcornUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    protected String login() {
        return "login";
    }

    @GetMapping("/register")
    protected String registerGet(Model model) {
        model.addAttribute("userEditView", new UserEditView());
        return "userRegister";
    }

    @PostMapping("/register")
    protected String registerPost(@ModelAttribute("user") UserEditView userEditView,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", ERROR_RESULT_HAS_ERRORS);
            model.addAttribute("userEditView", new UserEditView());
            return "userRegister";
        }

        if (!validEmail(userEditView, model)) {
            model.addAttribute("userEditView", userEditView);
            return "userRegister";
        } else if (!validPassword(userEditView, model)) {
            model.addAttribute("userEditView", userEditView);
            return "userRegister";
        }

        userEditView.setNewPassword(passwordEncoder.encode(userEditView.getNewPassword()));
        AcornUser acornUser = new AcornUser(userEditView);
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
    protected String editProfile(@ModelAttribute("userEditView") UserEditView userEditView,
                                 BindingResult result,
                                 Model model) {
        if (!result.hasErrors()) {
            Optional<AcornUser> optionalAcornUser = userService.findById(userEditView.getId());
            if (optionalAcornUser.isPresent()) {
                AcornUser acornUser = optionalAcornUser.get();

                // EMAIL
                if (userEditView.getEmail() != null) {
                    if (!userEditView.getEmail().equals(acornUser.getEmail())) {
                        if (!validEmail(userEditView, model)) {
                            model.addAttribute("userEditView", userEditView);
                            return "userProfile";
                        }
                        updateEmail(userEditView, acornUser, model);
                    }
                }

                // PASSWORD
                if (userEditView.getOldPassword() != null &&
                    userEditView.getNewPassword() != null &&
                    userEditView.getNewPasswordCheck() != null) {
                    if (!validPassword(userEditView, model)) {
                        userEditView.clearPasswords();
                        model.addAttribute("userEditView", userEditView);
                        return "userProfile";
                    }
                    updatePassword(userEditView, acornUser, model);
                }

                userService.save(acornUser);
                updatePrincipal(acornUser);
            }
        }

        // Do not redirect in order to show user feedback on success
        userEditView.clearPasswords();
        model.addAttribute("userEditView", userEditView);
        return "userProfile";
    }

    private boolean validEmail(UserEditView userEditView, Model model) {
        if (!emailLongEnough(userEditView.getEmail())) {
            model.addAttribute("errorMessage", ERROR_EMAIL_INVALID);
            return false;
        } else if (!stringAnEmailAddress(userEditView.getEmail())) {
            model.addAttribute("errorMessage", ERROR_EMAIL_INVALID);
            return false;
        } else if (emailAlreadyUsed(userEditView.getEmail())) {
            model.addAttribute("errorMessage", ERROR_EMAIL_IN_USE);
            return false;
        }
        return true;
    }

    private boolean validPassword(UserEditView userEditView, Model model) {
        if (!passwordLongEnough(userEditView.getNewPassword())) {
            model.addAttribute("errorMessage", ERROR_PASSWORD_INVALID);
            return false;
        } else if (!newPasswordsMatch(userEditView)) {
            model.addAttribute("errorMessage", ERROR_PASSWORDS_NO_MATCH);
            return false;
        }
        return true;
    }

    private void updateEmail(UserEditView userEditView, AcornUser user, Model model) {
        if (!userEditView.getEmail().equals(user.getEmail())) {
            user.setEmail(userEditView.getEmail());
            model.addAttribute("successMessage", INFO_EMAIL_UPDATE_SUCCESS);
        }
    }

    private void updatePassword(UserEditView userEditView, AcornUser acornUser, Model model) {
        if (oldPasswordsMatch(userEditView, acornUser)) {
            if (newPasswordsMatch(userEditView)) {
                acornUser.setPassword(passwordEncoder.encode(userEditView.getNewPassword()));
                model.addAttribute("successMessage", INFO_PASSWORD_UPDATE_SUCCESS);
            }
        } else {
            model.addAttribute("errorMessage", ERROR_PASSWORD_INCORRECT);
        }
    }

    public static boolean emailLongEnough(String email) {
        return email.length() > AcornUser.MINIMAL_EMAIL_LENGTH;
    }

    // TODO - Fix magic string, add to separate class?
    public static boolean stringAnEmailAddress(String email) {
        String emailPattern = "^(.+)@(\\S+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

    public static boolean passwordLongEnough(String password) {
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
