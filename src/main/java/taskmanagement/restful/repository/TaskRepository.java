package taskmanagement.restful.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import taskmanagement.restful.entity.Task;
import taskmanagement.restful.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
    Optional<Task> findFirstByUserAndId(User user, Integer id);
}
