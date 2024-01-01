package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Transactional
    public void updateUser(User newUser) {
        User oldUser = userRepository.findByEmail(newUser.getEmail()).get();
        oldUser.setName(newUser.getName());
        oldUser.setAge(newUser.getAge());
        oldUser.setPassword(newUser.getPassword());
        userRepository.save(oldUser);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void removeUserById(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void removeRoleByIdAndRole(int id, String role) {
        findById(id).getRoles().removeIf(r -> role.equals(r.getRole()));
    }

    @Transactional
    public void updateRoles(int id, Role role) {
        User user = findById(id);
        user.getRoles().add(role);
    }
}
