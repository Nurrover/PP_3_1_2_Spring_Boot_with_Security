package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import web.util.RolesAddValid;
import web.util.RolesDeletedValid;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RolesDeletedValid rolesDeletedValid;
    private final RoleService roleService;
    private final RolesAddValid rolesAddValid;

    @Autowired
    public AdminController(UserService userService, RolesDeletedValid rolesDeletedValid, RoleService roleService, RolesAddValid rolesAddValid) {
        this.userService = userService;
        this.rolesDeletedValid = rolesDeletedValid;
        this.roleService = roleService;
        this.rolesAddValid = rolesAddValid;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/show-all-users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/show-user";
    }

    @GetMapping("/new")
    public String formCreateUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        user.setRoles(new ArrayList<>(List.of(new Role("ROLE_USER"))));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        userService.updateUser(user);
        return "admin/show-user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/roles")
    public String editRoles(@PathVariable("id") int id, Model model, @ModelAttribute("newRole") Role role) {
        User user = userService.findById(id);
        List<Role> availableRole = roleService.findAllRole()
                .stream()
                .filter(r -> !user.getRoles().contains(r))
                .toList();

        model.addAttribute("user", user);
        model.addAttribute("availableRole", availableRole);

        return "admin/roles";
    }

    @PatchMapping("/{id}/roles")
    public String updateRoles(@ModelAttribute("user") @Valid Role newRole, BindingResult bindingResult,
                              @PathVariable("id") int id) {
        if (userService.findById(id).getRoles().contains(newRole)) {
            rolesAddValid.validate(newRole, bindingResult);
        } else {
            rolesAddValid.validate(newRole, bindingResult);
        }

        System.out.println(newRole);

        userService.updateRoles(id, newRole);
        return "admin/show-user";
    }

    @DeleteMapping("/{id}/roles")
    public String deletedRole(@ModelAttribute("deleteRole") @Valid Role role, BindingResult bindingResult,
                              @PathVariable("id") int id) {

        rolesDeletedValid.validate(role.getRole(), bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/roles";
        }

        userService.removeRoleByIdAndRole(id, role.getRole());
        return "admin/roles";
    }
}
