package boss.service;

import boss.dto.request.InstructorRequest;
import boss.dto.response.InstructorResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface InstructorService {

    SimpleResponse save(InstructorRequest instructorRequest);


   List<InstructorResponse> getAllInstructors();

    InstructorResponse findById(Long id);

    SimpleResponse update(Long id, InstructorRequest instructorRequest);

    SimpleResponse deleteById(Long id);

    SimpleResponse assignInstructorToCompany(Long instructorId, Long companyId);
}
