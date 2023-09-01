package boss.service.impl;

import boss.dto.request.InstructorRequest;
import boss.dto.response.InstructorResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Company;
import boss.entities.Instructor;
import boss.exception.NotFoundException;
import boss.repo.CompanyRepo;
import boss.repo.InstructorRepo;
import boss.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepo instructorRepo;
    private final CompanyRepo companyRepo;


    @Override
    public SimpleResponse save(InstructorRequest instructorRequest) {
        Instructor instructor=new Instructor();
        instructor.setFirstName(instructorRequest.firstName());
        instructor.setLastName(instructorRequest.lastName());
        instructor.setPhoneNumber(instructorRequest.phoneNumber());
        instructor.setSpecialization(instructorRequest.specialization());
        instructorRepo.save(instructor);
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
}
