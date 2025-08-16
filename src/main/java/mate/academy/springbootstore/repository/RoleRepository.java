package mate.academy.springbootstore.repository;

import java.util.Optional;
import mate.academy.springbootstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName name);
}
