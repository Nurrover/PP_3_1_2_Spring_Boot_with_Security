package web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.model.Role;
import web.service.RoleService;

@Component
public class RolesAddValid implements Validator {

    private final RoleService roleService;

    @Autowired
    public RolesAddValid(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Role.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Role role = (Role) target;
        if (! roleService.findAllRole().contains(role)) {
            errors.rejectValue("role", "", "Такой роли не существует: " + role.getRole());
        } else {
            errors.rejectValue("role", "", "Такая роль уже есть у пользователя: " + role.getRole());
        }
    }
}
