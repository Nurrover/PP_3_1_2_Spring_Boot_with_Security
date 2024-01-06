package web.service;

import web.model.Role;


import java.util.List;

public interface RoleService {

    List<Role> findAllRole();

    void saveRole(Role role);
    List<Role> findRole(String roleUser);
}
