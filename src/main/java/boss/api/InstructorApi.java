package boss.api;

import boss.dto.request.InstructorRequest;
import boss.dto.response.FullInstructorInfoResponse;
import boss.dto.response.InstructorResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.CompanyService;
import boss.service.InstructorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
@Tag(name = "InstructorApi")
public class InstructorApi {

    private final InstructorService instructorService;
    private final CompanyService companyService;

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping
    public List<InstructorResponse> getAllIns() {
        return instructorService.getAllInstructors();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid InstructorRequest instructorRequest) {
        return instructorService.save(instructorRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping("/{id}")
    public InstructorResponse findById(@PathVariable Long id) {
        return instructorService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateIns(@PathVariable Long id,
                                    @RequestBody @Valid InstructorRequest instructorRequest) {
        return instructorService.update(id, instructorRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteIns(@PathVariable Long id){
        return instructorService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/{instructorId}/{companyId}")
    public SimpleResponse assignInstructorToCompany(@PathVariable Long instructorId,
                                                    @PathVariable Long companyId){
       return instructorService.assignInstructorToCompany(instructorId,companyId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/full/{id}")
    public FullInstructorInfoResponse fullInstructorInfoResponse(@PathVariable Long id) {
        return instructorService.getFullInstructorInfo(id);
    }
    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize
    ) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = instructorService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }

}
