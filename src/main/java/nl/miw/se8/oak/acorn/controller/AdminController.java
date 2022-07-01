package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Pantry;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.PantryService;
import nl.miw.se8.oak.acorn.service.RoleService;
import nl.miw.se8.oak.acorn.viewmodel.AdminPantryOverviewVM;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.UserOverviewVM;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 30-6-2022
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    AcornUserService acornUserService;
    RoleService roleService;
    PantryService pantryService;

    public AdminController(AcornUserService acornUserService, RoleService roleService, PantryService pantryService) {
        this.acornUserService = acornUserService;
        this.roleService = roleService;
        this.pantryService = pantryService;
    }

    @GetMapping("/users")
    protected String userOverview(Model model) {
        List<AcornUser> allAcornUsers = acornUserService.findAll();
        List<UserOverviewVM> allUsersModelViewUsers = new ArrayList<>();
        Mapper mapper = new Mapper();
        for (AcornUser user : allAcornUsers) {
            allUsersModelViewUsers.add(mapper.userToUserOverviewVM(user));
        }
        model.addAttribute("users", allUsersModelViewUsers);
        return "userOverview";
    }

    @GetMapping("/users/{userId}")
    protected String userDetails(@PathVariable("userId") Long userId, Model model) {
        Optional<AcornUser> acornUser = acornUserService.findById(userId);
        Mapper mapper = new Mapper();
        if (acornUser.isPresent()) {
            model.addAttribute("user", mapper.userToUserOverviewVM(acornUser.get()));
            return "userDetails";
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{userId}/delete")
    protected String userDelete(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        Optional<AcornUser> optionalAcornUser = acornUserService.findById(userId);
        if (optionalAcornUser.isPresent()) {

            // Sysadmin can remove themselves, but there must always be at least one sysadmin
            if (acornUserIsCurrentUser(optionalAcornUser.get())) {
                if (acornUserService.moreThanOneSysAdmin()) {
                    acornUserService.deleteById(userId);
                    SecurityContextHolder.clearContext();
                    return "redirect:/";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Cannot remove: there must be at least one systems administrator.");
                    return "redirect:/admin/users";
                }
            }

            acornUserService.deleteById(userId);
            redirectAttributes.addFlashAttribute("successMessage", String.format("Successfully removed %s!", optionalAcornUser.get().getEmail()));
        }

        return "redirect:/admin/users";
    }

    // TODO - Fields should re-autofill after error messages are shown.
    @PostMapping("users/{userId}/update")
    protected String userUpdate(@Valid @ModelAttribute("user") UserOverviewVM userOverviewVM, BindingResult result) {
        if (result.hasErrors()) {
            return "userDetails";
        } else if (acornUserService.emailInUseBySomeoneElse(userOverviewVM)) {
            result.addError(new FieldError("user", "email", UserController.ERROR_EMAIL_IN_USE));
            return "userDetails";
        }

        AcornUser updatedUser = acornUserService.updateCurrentUser(userOverviewVM);
        SecurityController.updatePrincipal(updatedUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/pantries")
    protected String pantrySelection(Model model) {
        List<Pantry> pantries = pantryService.findAll();
        List<AdminPantryOverviewVM> adminPantryOverviewVMs = new ArrayList<>();
        for (Pantry pantry: pantries) {
            adminPantryOverviewVMs.add(Mapper.pantryToAdminPantryOverviewVM(pantry));
        }
        model.addAttribute("pantries", adminPantryOverviewVMs);
        return "pantrySelection";
    }

    private boolean acornUserIsCurrentUser(AcornUser acornUser) {
        return SecurityController.getCurrentUser().getEmail().equals(acornUser.getEmail());
    }

}
