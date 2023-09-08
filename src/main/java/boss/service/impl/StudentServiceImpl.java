package boss.service.impl;

import boss.dto.request.StudentRequest;
import boss.dto.response.PaginationResponse;
import boss.dto.response.StudentResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Group;
import boss.entities.Student;
import boss.exception.NotFoundException;
import boss.repo.GroupRepo;
import boss.repo.StudentRepo;
import boss.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;



    @Override
    public StudentResponse save(Long groupId, StudentRequest studentRequest) {
        Student student = new Student();
        student.setFirstName(studentRequest.firstName());
        student.setLastName(studentRequest.lastName());
        student.setPhoneNumber(studentRequest.phoneNumber());
        student.setEmail(studentRequest.email());
        student.setStudyFormat(studentRequest.studyFormat());
        student.setBlock(false);
        Group group = groupRepo.findById(groupId).orElseThrow(() ->
                new NotFoundException(String.format("Group with id: %s not found", groupId)));
        student.setGroup(group);
        studentRepo.save(student);
        log.info("Student successfully saved to group id: "+groupId);
        return StudentResponse.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .phoneNumber(student.getPhoneNumber())
                .email(student.getEmail())
                .studyFormat(student.getStudyFormat())
                .isBlock(false)
                .build();
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        return studentRepo.getAllStudents();
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        return studentRepo.getStudentById(id).orElseThrow(()->
                new NotFoundException(String.format("Student with id: %s not found",id)));
    }

    @Override
    public SimpleResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepo.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Student with id: %s not found", id)));
        student.setFirstName(studentRequest.firstName());
        student.setLastName(studentRequest.lastName());
        student.setPhoneNumber(studentRequest.phoneNumber());
        student.setEmail(studentRequest.email());
        student.setStudyFormat(studentRequest.studyFormat());
        student.setBlock(true);
        studentRepo.save(student);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with id: "+id+" successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteStudent(Long id) {
        if (!studentRepo.existsById(id)){
            throw new NotFoundException(String.format(
                    "Student with id: %s not found",id
            ));
        }
        studentRepo.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Student with id: "+id+" successfully deleted")
                .build();
    }

    @Override
    public List<StudentResponse> getAllOnlineStudents() {
        return studentRepo.getAllOnlineStudents();
    }

    @Override
    public List<StudentResponse> getAllOfflineStudents() {
        return studentRepo.getAllOfflineStudents();
    }

    @Override
    public PaginationResponse getAllPagination(int currentPage, int pageSize) {
        Pageable pageable= PageRequest.of(currentPage-1,pageSize);
        Page<StudentResponse>students=studentRepo.findAllStudents(pageable);
        return PaginationResponse.builder()
                .t(Collections.singletonList(students.getContent()))
                .currentPage(students.getNumber())
                .pageSize(students.getTotalPages())
                .build();
    }


}
