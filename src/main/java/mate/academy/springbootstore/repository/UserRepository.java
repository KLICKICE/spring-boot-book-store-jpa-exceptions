package mate.academy.springbootstore.repository;

import jakarta.validation.constraints.*;
import mate.academy.springbootstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@NotBlank @Email String email);
}
