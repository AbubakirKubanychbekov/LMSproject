package boss.api;

import boss.dto.request.InstructorRequest;
import boss.dto.response.InstructorResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.CompanyService;
import boss.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@RequiredArgsConstructor
public class InstructorApi {

    private final InstructorService instructorService;
    private final CompanyService companyService;

    @GetMapping
    public List<InstructorResponse> getAllIns() {
        return instructorService.getAllInstructors();
    }


    @PostMapping
    public SimpleResponse save(@RequestBody InstructorRequest instructorRequest) {
        return instructorService.save(instructorRequest);
    }

    @GetMapping("/{id}")
    public InstructorResponse findById(@PathVariable Long id) {
        return instructorService.findById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateIns(@PathVariable Long id,
                                    @RequestBody InstructorRequest instructorRequest) {
        return instructorService.update(id, instructorRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteIns(@PathVariable Long id){
        return instructorService.deleteById(id);
    }

    @PostMapping("/{instructorId}/{companyId}")
    public SimpleResponse assignInstructorToCompany(@PathVariable Long instructorId,
                                                    @PathVariable Long companyId){
       return instructorService.assignInstructorToCompany(instructorId,companyId);
    }


}
