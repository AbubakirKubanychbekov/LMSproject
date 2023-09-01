package boss.service.impl;

import boss.dto.request.CompanyRequest;
import boss.dto.response.CompanyResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Company;
import boss.exception.NotFoundException;
import boss.repo.CompanyRepo;
import boss.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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
        return companyRepo.getCompanyById(id).orElseThrow(()->
                new NotFoundException("Company with id: "+id+" not found"));
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
}
