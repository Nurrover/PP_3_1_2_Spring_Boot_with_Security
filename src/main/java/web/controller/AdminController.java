package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;
import web.util.EmailValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final EmailValidator emailValidator;
    private final PasswordEncoder encoder;

    @Autowired
    public AdminController(UserService userService1, RoleService roleService, EmailValidator emailValidator, PasswordEncoder encoder) {
        this.userService = userService1;
        this.roleService = roleService;
        this.emailValidator = emailValidator;
        this.encoder = encoder;
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
    public String formCreateUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.findAllRole());

        return "admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        emailValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("allRoles", roleService.findAllRole());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        userService.updateUser(user, id);
        return "admin/show-user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
