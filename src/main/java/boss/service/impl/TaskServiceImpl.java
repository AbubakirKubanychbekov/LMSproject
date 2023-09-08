package boss.service.impl;

import boss.dto.request.TaskRequest;
import boss.dto.response.PaginationResponse;
import boss.dto.response.TaskResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Lesson;
import boss.entities.Task;
import boss.exception.NotFoundException;
import boss.repo.LessonRepo;
import boss.repo.TaskRepo;
import boss.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final LessonRepo lessonRepo;


    @Override
    public TaskResponse saveTask(Long lessonId, TaskRequest taskRequest) {
        Task task =new Task();
        task.setTaskName(taskRequest.taskName());
        task.setTaskText(taskRequest.taskText());
        task.setDeadLine(taskRequest.deadLine());

        Lesson lesson = lessonRepo.findById(lessonId).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: %s not found", lessonId)));
        task.setLesson(lesson);
        taskRepo.save(task);
        log.info("Task successfully saved to lesson id: "+lessonId);
        return TaskResponse.builder()
                .taskName(task.getTaskName())
                .taskText(task.getTaskText())
                .deadLine(task.getDeadLine())
                .build();
    }

    @Override
    public List<TaskResponse> getAllTask() {
        return taskRepo.getAllTask();
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return taskRepo.getTaskById(id).orElseThrow(()->
                new NotFoundException(String.format("Task with id: %s not found",id)));
    }

    @Override
    public SimpleResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepo.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Task with id: %s not found", id)));
        task.setTaskName(taskRequest.taskName());
        task.setTaskText(taskRequest.taskText());
        task.setDeadLine(taskRequest.deadLine());
        taskRepo.save(task);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteTask(Long id) {
        if (!taskRepo.existsById(id)){
            throw new NotFoundException("Task with id: "+id+" not found");
        }
        taskRepo.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Task with id: "+id+" is deleted")
                .build();
    }

    @Override
    public PaginationResponse getAllPagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<TaskResponse>tasks=taskRepo.findAllTask(pageable);
        return PaginationResponse.builder()
                .t(Collections.singletonList(tasks.getContent()))
                .currentPage(tasks.getNumber())
                .pageSize(tasks.getTotalPages())
                .build();
    }

}
