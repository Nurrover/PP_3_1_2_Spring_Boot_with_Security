package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public List<Role> findRole(String roleUser) {
        return roleRepository.findByRole(roleUser);
    }
}
