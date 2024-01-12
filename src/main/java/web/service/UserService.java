package web.service;
import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findById(int id);

    Optional<User> findByEmail(String email);

    void updateUser(User newUser);

    List<User> getAllUsers();

    void saveUser(User user);

    void removeUserById(int id);
}
