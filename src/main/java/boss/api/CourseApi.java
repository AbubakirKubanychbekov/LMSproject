package boss.api;

import boss.dto.request.CourseRequest;
import boss.dto.response.CourseResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{companyId}")
@RequiredArgsConstructor
@Tag(name = "CourseApi")
public class CourseApi {

    private final CourseService courseService;

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping
    public List<CourseResponse> getAllCourses(@PathVariable Long companyId){
        return courseService.getAllCourses();
}
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PostMapping
    public CourseResponse save(@PathVariable Long companyId, @RequestBody CourseRequest courseRequest){
        return courseService.save(companyId,courseRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping("/{courseId}")
    public CourseResponse findById(@PathVariable Long companyId,
                                   @PathVariable Long courseId){
        return courseService.findCourseById(courseId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @PutMapping("/{courseId}")
    public SimpleResponse update(@PathVariable Long companyId,
                                 @PathVariable Long courseId,
                                 @RequestBody CourseRequest courseRequest){
        return courseService.updateCourse(courseId,courseRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @DeleteMapping("/{courseId}")
    public SimpleResponse deleteCourse(@PathVariable Long companyId,
                                       @PathVariable Long courseId){
        return courseService.delete(courseId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping("/sort")
    public List<CourseResponse> sortByDate(@PathVariable Long companyId){
        return courseService.sortCourseByStartDate(companyId);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize,
            @PathVariable Long companyId) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = courseService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }


}
