package boss.repo;

import boss.dto.response.TaskResponse;
import boss.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepo extends JpaRepository<Task,Long>{

    @Query("select new boss.dto.response.TaskResponse(t.id,t.taskName,t.taskText,t.deadLine) from Task t")
    List<TaskResponse> getAllTask();

    Optional<TaskResponse> getTaskById(Long id);

    @Query("select new boss.dto.response.TaskResponse(t.id,t.taskName,t.taskText,t.deadLine) from Task t")
    Page<TaskResponse> findAllTask(Pageable pageable);

}