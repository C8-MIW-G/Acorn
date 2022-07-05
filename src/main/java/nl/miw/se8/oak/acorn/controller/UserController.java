package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.UserEditVM;
import nl.miw.se8.oak.acorn.viewmodel.UserRegisterVM;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    public static final String ERROR_RESULT_HAS_ERRORS = "Something went wrong, try again.";
    public static final String ERROR_EMAIL_INVALID = "The e-mailaddress you entered is not valid.";
    public static final String ERROR_EMAIL_IN_USE = "That e-mailaddress is already in use.";
    public static final String ERROR_PASSWORD_INCORRECT = "You entered an incorrect password";
    public static final String ERROR_PASSWORD_INVALID = "The password you entered is not valid.";
    public static final String ERROR_PASSWORDS_NO_MATCH = "The passwords you entered are not an exact match.";
    public static final String INFO_EMAIL_UPDATE_SUCCESS = "Successfully changed your email!";
    public static final String INFO_PASSWORD_UPDATE_SUCCESS = "Successfully changed your password!";
    public static final String INFO_NAME_UPDATE_SUCCESS = "Successfully changed your username!";
    public static final String ERROR_NAME_TOO_SHORT = "The username you entered is too short!";

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
        model.addAttribute("userRegisterVM", new UserRegisterVM());
        return "userRegister";
    }

    @PostMapping("/register")
    protected String registerPost(@ModelAttribute("user") UserRegisterVM userRegisterVM, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", ERROR_RESULT_HAS_ERRORS);
            model.addAttribute("userRegisterVM", new UserRegisterVM());
            return "userRegister";
        }

        if (!validEmail(userRegisterVM.getEmail(), model) ||
                !validName(userRegisterVM.getName(), model) ||
                !validPassword(userRegisterVM.getPassword(), userRegisterVM.getPasswordCheck(), model)) {
            model.addAttribute("userRegisterVM", userRegisterVM);
            return "userRegister";
        }

        userRegisterVM.setPassword(passwordEncoder.encode(userRegisterVM.getPassword()));
        AcornUser acornUser = Mapper.userRegisterVMToUser(userRegisterVM);
        userService.save(acornUser);
        return "redirect:/pantrySelection";
    }

    @GetMapping("/profile")
    protected String profile(@AuthenticationPrincipal AcornUser acornUser, Model model) {
        UserEditVM userEditVM = Mapper.userToUserEditVM(acornUser);
        model.addAttribute("userEditVM", userEditVM);
        return "userProfile";
    }

//    @PostMapping("/profile")
//    protected String editProfile(@ModelAttribute("userEditView") UserEditVM userEditView,
//                                 BindingResult result,
//                                 Model model) {
//        if (!result.hasErrors()) {
//            Optional<AcornUser> optionalAcornUser = userService.findById(userEditView.getId());
//            if (optionalAcornUser.isPresent()) {
//                AcornUser acornUser = optionalAcornUser.get();
//
//                // EMAIL
//                if (userEditView.getEmail() != null) {
//                    if (!userEditView.getEmail().equals(acornUser.getEmail())) {
//                        if (!validEmail(userEditView, model)) {
//                            model.addAttribute("userEditView", userEditView);
//                            return "userProfile";
//                        }
//                        updateEmail(userEditView, acornUser, model);
//                    }
//                }
//
//                // PROFILE NAME
//                if (userEditView.getNewName() != null) {
//                    if (!validName(userEditView, model)) {
//                        model.addAttribute("userEditView", userEditView);
//                        return "userProfile";
//                    }
//                    updateName(userEditView, acornUser, model);
//                }
//
//                // PASSWORD
//                if (userEditView.getOldPassword() != null &&
//                    userEditView.getNewPassword() != null &&
//                    userEditView.getNewPasswordCheck() != null) {
//                    if (!validPassword(userEditView, model)) {
//                        userEditView.clearPasswords();
//                        model.addAttribute("userEditView", userEditView);
//                        return "userProfile";
//                    }
//                    updatePassword(userEditView, acornUser, model);
//                }
//
//                userService.save(acornUser);
//                SecurityController.updatePrincipal(acornUser);
//            }
//        }
//
//        // Do not redirect in order to show user feedback on success
//        userEditView.clearPasswords();
//        model.addAttribute("userEditView", userEditView);
//        return "userProfile";
//    }

    private boolean validEmail(String email, Model model) {
        if (!emailLongEnough(email)) {
            model.addAttribute("errorMessage", ERROR_EMAIL_INVALID);
            return false;
        } else if (!stringAnEmailAddress(email)) {
            model.addAttribute("errorMessage", ERROR_EMAIL_INVALID);
            return false;
        } else if (emailAlreadyUsed(email)) {
            model.addAttribute("errorMessage", ERROR_EMAIL_IN_USE);
            return false;
        }
        return true;
    }

    private boolean validPassword(String password, String passwordCheck, Model model) {
        if (!passwordLongEnough(password)) {
            model.addAttribute("errorMessage", ERROR_PASSWORD_INVALID);
            return false;
        } else if (!newPasswordsMatch(password, passwordCheck)) {
            model.addAttribute("errorMessage", ERROR_PASSWORDS_NO_MATCH);
            return false;
        }
        return true;
    }

    private boolean validName(String name, Model model) {
        if (name.length() < AcornUser.MINIMAL_NAME_LENGTH) {
            model.addAttribute("errorMessage", ERROR_NAME_TOO_SHORT);
            return false;
        }
        return true;
    }

    private void updateEmail(UserEditVM userEditVM, AcornUser user, Model model) {
        if (!userEditVM.getEmail().equals(user.getEmail())) {
            user.setEmail(userEditVM.getEmail());
            model.addAttribute("successMessage", INFO_EMAIL_UPDATE_SUCCESS);
        }
    }

    private void updatePassword(UserEditVM userEditVM, AcornUser acornUser, Model model) {
        if (oldPasswordsMatch(userEditVM, acornUser)) {
            if (newPasswordsMatch(userEditVM.getNewPassword(), userEditVM.getNewPasswordCheck())) {
                acornUser.setPassword(passwordEncoder.encode(userEditVM.getNewPassword()));
                model.addAttribute("successMessage", INFO_PASSWORD_UPDATE_SUCCESS);
            }
        } else {
            model.addAttribute("errorMessage", ERROR_PASSWORD_INCORRECT);
        }
    }

    private void updateName(UserEditVM userEditVM, AcornUser acornUser, Model model) {
        if (!userEditVM.getNewName().equals(acornUser.getName())) {
            acornUser.setName(userEditVM.getNewName());
            userEditVM.setName(userEditVM.getNewName());
            model.addAttribute("successMessage", INFO_NAME_UPDATE_SUCCESS);
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

    private boolean oldPasswordsMatch(UserEditVM userEditVM, AcornUser acornUser) {
        return passwordEncoder.matches(userEditVM.getOldPassword(), acornUser.getPassword());
    }

    private boolean newPasswordsMatch(String password, String passwordCheck) {
        return password.equals(passwordCheck);
    }

    public static boolean passwordLongEnough(String password) {
        return password.length() > AcornUser.MINIMAL_PASSWORD_LENGTH;
    }

    // TODO - Is this good practice?


}
