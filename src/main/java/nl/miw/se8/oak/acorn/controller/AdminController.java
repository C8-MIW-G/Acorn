package nl.miw.se8.oak.acorn.controller;

import nl.miw.se8.oak.acorn.model.AcornUser;
import nl.miw.se8.oak.acorn.service.AcornUserService;
import nl.miw.se8.oak.acorn.service.RoleService;
import nl.miw.se8.oak.acorn.viewmodel.Mapper;
import nl.miw.se8.oak.acorn.viewmodel.UserOverviewVM;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "redirect:/users";
    }

    @GetMapping("/users/{userId}/delete")
    protected String userDelete(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes) {
        Optional<AcornUser> optionalAcornUser = acornUserService.findById(userId);
        if (optionalAcornUser.isPresent()) {

            // There must always be at least one sysadmin
            if (optionalAcornUser.get().getEmail().equals(SecurityController.getCurrentUser().getEmail())) {
                if (acornUserService.moreThanOneSysAdmin()) {
                    acornUserService.deleteById(userId);
                    SecurityContextHolder.clearContext();
                    return "redirect:/";
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Cannot remove: there must be at least one systems administrator.");
                    return "redirect:/users";
                }
            }
            acornUserService.deleteById(userId);
            redirectAttributes.addFlashAttribute("successMessage",
                    String.format("Successfully removed %s!", optionalAcornUser.get().getEmail()));
        }

        return "redirect:/users";
    }

    @PostMapping("users/{userId}/update")
    protected String userUpdate(@Valid @ModelAttribute("user") UserOverviewVM acornUser, BindingResult result) {
        if (result.hasErrors()) {
            return "userDetails";
        }

        Optional<AcornUser> dbAcornUser = acornUserService.findById(acornUser.getId());
        if (dbAcornUser.isPresent()) {
            dbAcornUser.get().setEmail(acornUser.getEmail());
            dbAcornUser.get().setName(acornUser.getName());
            acornUserService.save(dbAcornUser.get());
            SecurityController.updatePrincipal(dbAcornUser.get());
        }
        return "redirect:/users";
    }


}
