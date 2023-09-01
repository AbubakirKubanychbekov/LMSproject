package boss.service;

import boss.dto.request.StudentRequest;
import boss.dto.response.StudentResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface StudentService {
    StudentResponse save(Long groupId, StudentRequest studentRequest);

    List<StudentResponse> getAllStudents();

    StudentResponse getStudentById(Long id);

    SimpleResponse updateStudent(Long id, StudentRequest studentRequest);

    SimpleResponse deleteStudent(Long id);
}
