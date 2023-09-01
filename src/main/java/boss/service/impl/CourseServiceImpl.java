package boss.service.impl;

import boss.dto.request.CourseRequest;
import boss.dto.response.CourseResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Company;
import boss.entities.Course;
import boss.exception.NotFoundException;
import boss.repo.CompanyRepo;
import boss.repo.CourseRepo;
import boss.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;
    private final CompanyRepo companyRepo;

    @Override
    public CourseResponse save(Long companyId, CourseRequest courseRequest) {
        Course course =new Course();
        course.setCourseName(courseRequest.courseName());
        course.setDateOfStart(courseRequest.dateOfStart());
        course.setDescription(courseRequest.description());
        Company company = companyRepo.findById(companyId).orElseThrow(() -> new NotFoundException("Company with id: " + companyId + " not found"));
        course.setCompany(company);
        courseRepo.save(course);
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .dateOfStart(course.getDateOfStart())
                .description(course.getDescription())
                .build();
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepo.getAllCourses();
    }

    @Override
    public CourseResponse findCourseById(Long courseId) {
        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new NotFoundException("Course with id: " + courseId + " not found"));
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .dateOfStart(course.getDateOfStart())
                .description(course.getDescription())
                .build();
    }

    @Override
    public SimpleResponse updateCourse(Long courseId, CourseRequest courseRequest) {
        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new NotFoundException("Course with id: " + courseId + " not found"));
        course.setCourseName(courseRequest.courseName());
        course.setDateOfStart(courseRequest.dateOfStart());
        course.setDescription(courseRequest.description());
        courseRepo.save(course);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Course successfully updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long courseId) {
        if (!courseRepo.existsById(courseId)){
            throw new NotFoundException(
                    "Course with id: "+courseId+" not found!");
        }
        courseRepo.deleteById(courseId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully course is deleted")
                .build();
    }

}
