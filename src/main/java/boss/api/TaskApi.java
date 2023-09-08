package boss.api;

import boss.dto.request.TaskRequest;
import boss.dto.response.PaginationResponse;
import boss.dto.response.TaskResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@Tag(name = "TaskApi")
public class TaskApi {

    private final TaskService taskService;
   @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    @GetMapping
    public List<TaskResponse> getAllTask() {
        return taskService.getAllTask();
    }


    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PostMapping("/{lessonId}")
    public TaskResponse saveTask(@PathVariable Long lessonId,
                                 @RequestBody TaskRequest taskRequest) {
        return taskService.saveTask(lessonId, taskRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PutMapping("/{id}")
    public SimpleResponse updateTask(@PathVariable Long id,
                                     @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize
    ) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = taskService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }

}
