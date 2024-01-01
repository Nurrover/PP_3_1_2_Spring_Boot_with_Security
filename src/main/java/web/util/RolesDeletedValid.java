package web.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.model.User;

@Component
public class RolesDeletedValid implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String role = (String) target;
        if ("ROLE_USER".equals(role)) {
            errors.rejectValue("role", "", "Невозможно удалить роль: ROLE_USER");
        }
    }
}
