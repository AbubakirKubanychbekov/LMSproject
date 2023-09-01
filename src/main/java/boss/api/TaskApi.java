package boss.api;

import boss.dto.request.TaskRequest;
import boss.dto.response.TaskResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskApi {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getAllTask(){
      return taskService.getAllTask();
    }



    @PostMapping("/{lessonId}")
    public TaskResponse saveTask(@PathVariable Long lessonId,
                                 @RequestBody TaskRequest taskRequest){
        return taskService.saveTask(lessonId,taskRequest);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateTask(@PathVariable Long id,
                                     @RequestBody TaskRequest taskRequest){
        return taskService.updateTask(id,taskRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id);
    }
}
