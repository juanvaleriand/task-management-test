package taskmanagement.restful.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taskmanagement.restful.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByUsername(String username);
    Optional<User> findFirstByToken(String token);
}
