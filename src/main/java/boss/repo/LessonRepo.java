package boss.repo;

import boss.dto.response.LessonResponse;
import boss.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface LessonRepo extends JpaRepository<Lesson,Long> {


    @Query("SELECT new boss.dto.response.LessonResponse(l.id,l.lessonName) FROM Lesson l")
    List<LessonResponse> getAllLessons();

    Optional<LessonResponse>getLessonById(Long id);
}
