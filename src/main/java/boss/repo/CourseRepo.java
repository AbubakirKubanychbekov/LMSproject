package boss.repo;

import boss.dto.response.CourseResponse;
import boss.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course,Long> {

    @Query("select new boss.dto.response.CourseResponse(c.id,c.courseName,c.dateOfStart,c.description) from Course c")
    List<CourseResponse> getAllCourses();


    Optional<CourseResponse>getCourseById(Long courseId);

    Optional<CourseResponse> findByIdAndCompanyId(Long courseId, Long companyId);

    Optional<CourseResponse> findCourseById(Long courseId);
}
