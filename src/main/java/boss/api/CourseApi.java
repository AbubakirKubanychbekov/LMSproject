package boss.api;

import boss.dto.request.CourseRequest;
import boss.dto.response.CourseResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{companyId}")
@RequiredArgsConstructor
public class CourseApi {

    private final CourseService courseService;

    @GetMapping
    public List<CourseResponse> getAllCourses(@PathVariable Long companyId){
        return courseService.getAllCourses();
}

    @PostMapping
    public CourseResponse save(@PathVariable Long companyId, @RequestBody CourseRequest courseRequest){
        return courseService.save(companyId,courseRequest);
    }

    @GetMapping("/{courseId}")
    public CourseResponse findById(@PathVariable Long companyId,
                                   @PathVariable Long courseId){
        return courseService.findCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    public SimpleResponse update(@PathVariable Long companyId,
                                 @PathVariable Long courseId,
                                 @RequestBody CourseRequest courseRequest){
        return courseService.updateCourse(courseId,courseRequest);
    }

    @DeleteMapping("/{courseId}")
    public SimpleResponse deleteCourse(@PathVariable Long companyId,
                                       @PathVariable Long courseId){
        return courseService.delete(courseId);
    }

}
