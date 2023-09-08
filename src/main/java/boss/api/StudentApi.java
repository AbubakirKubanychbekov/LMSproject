package boss.api;

import boss.dto.request.StudentRequest;
import boss.dto.response.PaginationResponse;
import boss.dto.response.StudentResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "StudentApi")
public class StudentApi {

    private final StudentService studentService;

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping("/{groupId}")
    public StudentResponse save(@PathVariable Long groupId,
                                @RequestBody @Valid StudentRequest studentRequest) {
        return studentService.save(groupId, studentRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    @GetMapping("{id}")
    public StudentResponse getById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PutMapping("/{id}")
    public SimpleResponse updateStudent(@PathVariable Long id,
                                        @RequestBody @Valid StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }



    @GetMapping("/filter")
    @Secured({"ADMIN", "INSTRUCTOR"})
    public List<StudentResponse> filterStudentsByFormat(@RequestParam("format") String format) {
        if ("online".equalsIgnoreCase(format)) {
            return studentService.getAllOnlineStudents();

        } else if ("offline".equalsIgnoreCase(format)) {
            return studentService.getAllOfflineStudents();

        } else {
            throw new IllegalArgumentException("Некорректный формат обучения: " + format);
        }
    }


    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize
    ) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = studentService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }

}
