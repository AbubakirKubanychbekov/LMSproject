package boss.repo;

import boss.dto.response.TaskResponse;
import boss.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task,Long>{

    @Query("select new boss.dto.response.TaskResponse(t.id,t.taskName,t.taskText,t.deadLine) from Task t")
    List<TaskResponse> getAllTask();

    Optional<TaskResponse> getTaskById(Long id);
}
