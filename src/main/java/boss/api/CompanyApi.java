package boss.api;

import boss.dto.request.CompanyRequest;
import boss.dto.response.CompanyResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyApi {

    private final CompanyService companyService;


    @GetMapping
    public List<CompanyResponse> getAllCompanies(){
        return companyService.getAllCompanies();
    }



    @PostMapping
    public CompanyResponse saveCompany(@RequestBody CompanyRequest companyRequest){
        return companyService.saveCompany(companyRequest);
    }

    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id){
       return companyService.getCompanyById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateCompany(@PathVariable Long id,
                                        @RequestBody CompanyRequest companyRequest){
        return companyService.updateCompany(id,companyRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteCompany(@PathVariable Long id){
        return companyService.deleteById(id);
    }

  }
