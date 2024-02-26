package lt.tastybytes.receptaiserver.repository;

import lt.tastybytes.receptaiserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}