// src/main/java/se/inyat/marketplace/repository/UserRepository.java
package se.inyat.marketplace.repository;

import se.inyat.marketplace.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

