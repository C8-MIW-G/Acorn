package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.model.Role;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.RoleService;
import nl.miw.se8.oak.acorn.viewmodel.UserManagementViewModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Auteur: Thijs van Blanken
 * Aangemaakt op: 30-6-2022
 */
@Controller
public class AdminController {

    AcornUserService acornUserService;
    RoleService roleService;

    public AdminController(AcornUserService acornUserService, RoleService roleService) {
        this.acornUserService = acornUserService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    protected String userOverview(Model model) {
        List<AcornUser> allAcornUsers = acornUserService.findAll();
        List<UserManagementViewModel> allUsersModelViewUsers = new ArrayList<>();
        for (AcornUser user : allAcornUsers) {
            allUsersModelViewUsers.add(new UserManagementViewModel(user));
        }
        model.addAttribute("users", allUsersModelViewUsers);
        return "userOverview";
    }

    @GetMapping("/users/{userId}/delete")
    protected String userDelete(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        Optional<AcornUser> optionalAcornUser = acornUserService.findById(userId);
        if (optionalAcornUser.isPresent()) {

            // There must always be at least one sysadmin
            if (optionalAcornUser.get().getEmail().equals(getCurrentUser().getEmail())) {
                if (currentUserIsOnlySysAdmin()) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Cannot remove: there must be at least one systems administrator.");
                    return "redirect:/users";
                } else {
                    acornUserService.deleteById(userId);
                    SecurityContextHolder.clearContext();
                    return "redirect:/";
                }
            }
            acornUserService.deleteById(userId);
            redirectAttributes.addFlashAttribute("successMessage",
                    String.format("Successfully removed %s!", optionalAcornUser.get().getEmail()));
        }

        return "redirect:/users";
    }

    private AcornUser getCurrentUser() {
        return (AcornUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean currentUserIsOnlySysAdmin() {
        Role adminRole = roleService.findByName("ROLE_ADMIN").get();
        List<AcornUser> sysAdmins = acornUserService.findByRolesContains(adminRole);
        return sysAdmins.size() <= 1;
    }

}
