package boss.service;

import boss.dto.request.CourseRequest;
import boss.dto.response.CourseResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface CourseService {
    CourseResponse save(Long companyId, CourseRequest courseRequest);

    List<CourseResponse> getAllCourses();

    CourseResponse findCourseById(Long courseId);

    SimpleResponse updateCourse(Long courseId, CourseRequest courseRequest);

    SimpleResponse delete(Long courseId);
}
