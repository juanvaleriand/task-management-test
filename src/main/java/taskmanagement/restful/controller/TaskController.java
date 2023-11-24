package taskmanagement.restful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import taskmanagement.restful.entity.User;
import taskmanagement.restful.model.CreateTaskRequest;
import taskmanagement.restful.model.PagingResponse;
import taskmanagement.restful.model.SearchTaskRequest;
import taskmanagement.restful.model.TaskResponse;
import taskmanagement.restful.model.UpdateTaskRequest;
import taskmanagement.restful.model.WebResponse;
import taskmanagement.restful.service.TaskService;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(
        path = "api/tasks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TaskResponse> create(User user, @RequestBody CreateTaskRequest request) {
        TaskResponse taskResponse = taskService.create(user, request);
        return WebResponse.<TaskResponse>builder().data(taskResponse).build();
    }

    @GetMapping(
        path = "/api/tasks/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TaskResponse> get(User user, @PathVariable("id") Integer id) {
        TaskResponse taskResponse = taskService.get(user, id);
        return WebResponse.<TaskResponse>builder().data(taskResponse).build();
    }

    @PutMapping(
        path = "/api/tasks/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TaskResponse> update(User user, 
    @RequestBody UpdateTaskRequest request, 
    @PathVariable("id") Integer id) {
        request.setId(id);

        TaskResponse taskResponse = taskService.update(user, request);
        return WebResponse.<TaskResponse>builder().data(taskResponse).build();
    }

    @DeleteMapping(
        path = "/api/tasks/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(User user, @PathVariable("id") Integer id) {
        taskService.delete(user, id);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/tasks",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<TaskResponse>> search(
        User user,
        @RequestParam(value = "title", required = false) String title, 
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "completed", required = false) Boolean completed,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page, 
        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        SearchTaskRequest request = SearchTaskRequest.builder()
                .page(page)
                .size(size)
                .title(title)
                .description(description)
                .completed(completed)
                .build();

        Page<TaskResponse> taskResponses = taskService.search(user, request);
        return WebResponse.<List<TaskResponse>>builder()
            .data(taskResponses.getContent())
            .paging(PagingResponse.builder()
            .currentPage(taskResponses.getNumber())
            .totalPage(taskResponses.getTotalPages())
            .size(taskResponses.getSize())
            .build())
            .build();
    }
}
