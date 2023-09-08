package boss.api;

import boss.dto.request.CompanyRequest;
import boss.dto.response.CompanyResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
@Tag(name = "CompanyApi")
public class CompanyApi {

    private final CompanyService companyService;

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping
    public List<CompanyResponse> getAllCompanies(){
        return companyService.getAllCompanies();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public CompanyResponse saveCompany(@RequestBody @Valid CompanyRequest companyRequest){
        return companyService.saveCompany(companyRequest);
    }
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping("/{id}")
    public CompanyResponse getById(@PathVariable Long id){
       return companyService.getCompanyById(id);
    }
@PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateCompany(@PathVariable Long id,
                                        @RequestBody @Valid CompanyRequest companyRequest){
        return companyService.updateCompany(id,companyRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteCompany(@PathVariable Long id){
        return companyService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping("/getCompanyDetails/{id}")
    public CompanyResponse getCompanyDetails(@PathVariable Long id) {
        return companyService.getCompanyDetails(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize
    ) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = companyService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }


}
