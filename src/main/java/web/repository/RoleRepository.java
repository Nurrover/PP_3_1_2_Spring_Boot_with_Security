package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByRole(String role);
}
