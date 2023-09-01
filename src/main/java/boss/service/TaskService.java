package boss.service;

import boss.dto.request.TaskRequest;
import boss.dto.response.TaskResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface TaskService {
    TaskResponse saveTask(Long lessonId, TaskRequest taskRequest);

    List<TaskResponse> getAllTask();

    TaskResponse getTaskById(Long id);

    SimpleResponse updateTask(Long id, TaskRequest taskRequest);

    SimpleResponse deleteTask(Long id);
}
