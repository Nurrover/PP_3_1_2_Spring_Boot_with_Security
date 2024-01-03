package web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.model.User;
import web.service.UserService;

@Component
public class EmailValidator implements Validator {

    private final UserService userService;

    @Autowired
    public EmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Пользователь с таким Email уже существует");
        }
        
    }
}
