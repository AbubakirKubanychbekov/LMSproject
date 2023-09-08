package boss.service.impl;

import boss.dto.request.InstructorRequest;
import boss.dto.response.FullInstructorInfoResponse;
import boss.dto.response.InstructorResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Company;
import boss.entities.Group;
import boss.entities.Instructor;
import boss.exception.NotFoundException;
import boss.repo.CompanyRepo;
import boss.repo.GroupRepo;
import boss.repo.InstructorRepo;
import boss.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepo instructorRepo;
    private final CompanyRepo companyRepo;
    private final GroupRepo groupRepo;


    @Override
    public SimpleResponse save(InstructorRequest instructorRequest) {
        Instructor instructor=new Instructor();
        instructor.setFirstName(instructorRequest.firstName());
        instructor.setLastName(instructorRequest.lastName());
        instructor.setPhoneNumber(instructorRequest.phoneNumber());
        instructor.setSpecialization(instructorRequest.specialization());
        instructorRepo.save(instructor);
        log.info("Instructor successfully saved");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor successfully saved")
                .build();
    }

    @Override
    public List<InstructorResponse> getAllInstructors() {
        return instructorRepo.getAllInstructors();
    }

    @Override
    public InstructorResponse findById(Long id) {
        return instructorRepo.getInstructorById(id).orElseThrow(()->
                new NotFoundException(String.format("Instructor with id: %s not found",id)));

    }

    @Override
    public SimpleResponse update(Long id, InstructorRequest instructorRequest) {
        Instructor instructor = instructorRepo.findById(id).orElseThrow(() -> new NotFoundException(String.format("Instructor with id: %s", id)));
        instructor.setFirstName(instructorRequest.firstName());
        instructor.setLastName(instructorRequest.lastName());
        instructor.setPhoneNumber(instructorRequest.phoneNumber());
        instructor.setSpecialization(instructorRequest.specialization());
        instructorRepo.save(instructor);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!instructorRepo.existsById(id)){
            throw new NotFoundException(String.format("Instructor with id: %s",id));
        }
        instructorRepo.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Instructor with id: %s successfully delete",id))
                .build();

    }

    @Transactional
    @Override
    public SimpleResponse assignInstructorToCompany(Long instructorId, Long companyId) {
        Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> new NotFoundException("Instructor not found with id: " + instructorId));

        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found with id: " + companyId));

        company.getInstructors().add(instructor);
        List<Company>companies=new ArrayList<>();
        companies.add(company);
        instructor.setCompanies(companies);
        instructorRepo.save(instructor);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Instructor successfully assign to Company")
                .build();
    }

    @Override
    public FullInstructorInfoResponse getFullInstructorInfo(Long id) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Instructor not found with id: " + id));

        Group group = groupRepo.findById(instructor.getId())
                .orElseThrow(() -> new NotFoundException("Group not found for instructor with id: " + id));

        int numberOfStudents = group.getStudents().size();

        return new FullInstructorInfoResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                group.getGroupName(),
                numberOfStudents);

    }

    @Override
    public PaginationResponse getAllPagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<InstructorResponse> instructors = instructorRepo.findAllInstructors(pageable);
        return PaginationResponse.builder()
                .t(Collections.singletonList(instructors.getContent()))
                .currentPage(instructors.getNumber())
                .pageSize(instructors.getTotalPages())
                .build();
    }
}
