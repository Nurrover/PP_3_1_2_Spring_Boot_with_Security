package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User findById(int id);

    Optional<User> findByEmail(String email);

    void updateUser(User newUser, int id);

    List<User> getAllUsers();

    void saveUser(User user);

    void removeUserById(int id);
}
