package web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;

@Component
public class RolesValid implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String role = (String) target;
        if ("ROLE_USER".equals(role)) {
            errors.rejectValue("delete", "", "Невозможно удалить роль: ROLE_USER");
        }
    }
}
