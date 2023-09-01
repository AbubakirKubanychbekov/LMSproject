package boss.repo;

import boss.dto.response.StudentResponse;
import boss.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student,Long> {


    @Query("SELECT new boss.dto.response.StudentResponse(s.id,s.firstName,s.lastName,s.phoneNumber,s.email,s.studyFormat,s.isBlock) FROM Student s")
    List<StudentResponse> getAllStudents();

    Optional<StudentResponse> getStudentById(Long id);
}
