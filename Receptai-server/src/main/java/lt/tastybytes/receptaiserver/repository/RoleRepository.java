package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}