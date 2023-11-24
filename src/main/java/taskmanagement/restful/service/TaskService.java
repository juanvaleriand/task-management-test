package taskmanagement.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import taskmanagement.restful.entity.Task;
import taskmanagement.restful.entity.User;
import taskmanagement.restful.model.CreateTaskRequest;
import taskmanagement.restful.model.SearchTaskRequest;
import taskmanagement.restful.model.TaskResponse;
import taskmanagement.restful.model.UpdateTaskRequest;
import taskmanagement.restful.repository.TaskRepository;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TaskResponse create(User user, CreateTaskRequest request) {
        validationService.validate(request);
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        task.setUser(user);

        taskRepository.save(task);

        return toTaskResponse(task);
    }

    private TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public TaskResponse get(User user, Integer id) {
        Task task = taskRepository.findFirstByUserAndId(user, id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task is not foud"));

        return toTaskResponse(task);
    }

    @Transactional
    public TaskResponse update(User user, UpdateTaskRequest request) {
        validationService.validate(request);

        Task task = taskRepository.findFirstByUserAndId(user, request.getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task is not found!"));

        String title = request.getTitle() != null ? request.getTitle() : task.getTitle();
        String description = request.getDescription() != null ? request.getDescription() : task.getDescription();

        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(request.getCompleted());
        taskRepository.save(task);

        return toTaskResponse(task);
    }

    @Transactional
    public void delete(User user, Integer id) {
        Task task = taskRepository.findFirstByUserAndId(user, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task is not found!"));
        
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> search(User user, SearchTaskRequest request) {
        Specification<Task> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("user"), user));
            if (Objects.nonNull(request.getTitle())) {
                predicates.add(builder.or(
                    builder.like(root.get("title"), "%" + request.getTitle() + "%")
                ));
            }

            if (Objects.nonNull(request.getDescription())) {
                predicates.add(builder.or(
                    builder.like(root.get("description"), "%" + request.getDescription() + "%")
                ));
            }

            if (Objects.nonNull(request.getCompleted())) {
                predicates.add(builder.equal(root.get("completed"), request.getCompleted()));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "id"));
        Page<Task> tasks = taskRepository.findAll(specification, pageable);
        List<TaskResponse> taskResponses = tasks.getContent().stream()
                .map(this::toTaskResponse)
                .toList();

        return new PageImpl<>(taskResponses, pageable, tasks.getTotalElements());
    }
}
