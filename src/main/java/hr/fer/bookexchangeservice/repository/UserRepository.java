package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByEmail(String email);
    Optional<UserDetail> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
