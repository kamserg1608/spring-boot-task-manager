package org.example.taskmanager.security.repository;
import org.example.taskmanager.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String username);

    boolean existsByLogin(String username);

    boolean existsByEmail(String email);
}