package boss.service;

import boss.dto.request.CompanyRequest;
import boss.dto.response.CompanyResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface CompanyService {
    CompanyResponse saveCompany(CompanyRequest companyRequest);
    List<CompanyResponse> getAllCompanies();
    CompanyResponse getCompanyById(Long id);
    SimpleResponse updateCompany(Long id, CompanyRequest companyRequest);

    SimpleResponse deleteById(Long id);
}
