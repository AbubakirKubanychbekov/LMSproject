package boss.api;

import boss.dto.request.LessonRequest;
import boss.dto.response.LessonResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
@Tag(name = "LessonApi")
public class LessonApi {

    private final LessonService lessonService;

    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
    @GetMapping
    public List<LessonResponse> getAllLessons(){
        return lessonService.getAllLessons();
    }


    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PostMapping("/{courseId}")
    public LessonResponse save(@PathVariable Long courseId,
                               @RequestBody LessonRequest lessonRequest){
        return lessonService.saveLesson(courseId,lessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','STUDENT')")
    @GetMapping("/{id}")
    public LessonResponse getById(@PathVariable Long id){
        return lessonService.getLessonById(id);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PutMapping("{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody LessonRequest lessonRequest){
        return lessonService.updateLesson(id,lessonRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteLesson(@PathVariable Long id){
        return lessonService.deleteLesson(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize
    ) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = lessonService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }

}
