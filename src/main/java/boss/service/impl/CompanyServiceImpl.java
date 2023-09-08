package boss.service.impl;

import boss.dto.request.CompanyRequest;
import boss.dto.response.CompanyResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Company;
import boss.exception.NotFoundException;
import boss.repo.CompanyRepo;
import boss.service.CompanyService;
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
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;


    @Override
    public CompanyResponse saveCompany(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setName(companyRequest.name());
        company.setCountry(companyRequest.country());
        company.setAddress(companyRequest.address());
        company.setPhoneNumber(companyRequest.phoneNumber());
        companyRepo.save(company);
        log.info("Company successfully saved");
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getCountry(),
                company.getAddress(),
                company.getPhoneNumber());
    }

    @Override
    public List<CompanyResponse> getAllCompanies() {
        return companyRepo.getAllCompanies();
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        return companyRepo.getCompanyById(id).orElseThrow(()-> {
            String message = "Company with id: " + id + " not found";
            log.error(message);
            return new NotFoundException(message);
        });
    }

    @Override
    public SimpleResponse updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = companyRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Company with id: " + id + " not found"));
        company.setName(companyRequest.name());
        company.setCountry(companyRequest.country());
        company.setAddress(companyRequest.address());
        company.setPhoneNumber(companyRequest.phoneNumber());
        companyRepo.save(company);
        return  SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SUCCESSFULLY UPDATED")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
       if (!companyRepo.existsById(id)){
           throw new NotFoundException(
                   "Company with id: "+id+" not found!");
       }
       companyRepo.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Company with id: "+id+" is deleted")
                .build();
    }

    @Override
    public CompanyResponse getCompanyDetails(Long id) {
        Company company = companyRepo.findById(id).orElseThrow(() ->
                new NoSuchElementException("Company with id: " + id + " not found"));
        company.setId(company.getId());
        company.setName(company.getName());
        company.setCountry(company.getCountry());
        company.setAddress(company.getAddress());
        company.setPhoneNumber(company.getPhoneNumber());
        company.setCourses(company.getCourses());
        company.setInstructors(company.getInstructors());
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .country(company.getCountry())
                .address(company.getAddress())
                .phoneNumber(company.getPhoneNumber())
                .build();
    }

    @Override
    public PaginationResponse getAllPagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<CompanyResponse>companies= companyRepo.getAllCompanies(pageable);
        return PaginationResponse.builder()
                .t(Collections.singletonList(companies.getContent()))
                .currentPage(companies.getNumber())
                .pageSize(companies.getTotalPages())
                .build();
    }
}