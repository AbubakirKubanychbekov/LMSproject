package boss.repo;

import boss.dto.response.LessonResponse;
import boss.entities.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LessonRepo extends JpaRepository<Lesson,Long> {


    @Query("SELECT new boss.dto.response.LessonResponse(l.id,l.lessonName) FROM Lesson l")
    List<LessonResponse> getAllLessons();

    Optional<LessonResponse>getLessonById(Long id);

    @Query("select new boss.dto.response.LessonResponse(l.id,l.lessonName) from Lesson l")
    Page<LessonResponse> findAllLessons(Pageable pageable);
}
