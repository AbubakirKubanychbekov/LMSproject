package boss.repo;

import boss.dto.response.InstructorResponse;
import boss.entities.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface InstructorRepo extends JpaRepository<Instructor,Long> {

    @Query("select new boss.dto.response.InstructorResponse(i.id,i.firstName,i.lastName,i.phoneNumber,i.specialization) from Instructor i")
    List<InstructorResponse> getAllInstructors();

    Optional<InstructorResponse> getInstructorById(Long id);

    @Query("select new boss.dto.response.InstructorResponse(i.id,i.firstName,i.lastName,i.phoneNumber,i.specialization) from Instructor i")
    Page<InstructorResponse> findAllInstructors(Pageable pageable);
}
