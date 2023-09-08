package boss.service.impl;

import boss.dto.request.LessonRequest;
import boss.dto.response.LessonResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Course;
import boss.entities.Lesson;
import boss.exception.NotFoundException;
import boss.repo.CourseRepo;
import boss.repo.LessonRepo;
import boss.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LessonServiceImpl implements LessonService {

    private final LessonRepo lessonRepo;
    private final CourseRepo courseRepo;

    @Override
    public LessonResponse saveLesson(Long courseId, LessonRequest lessonRequest) {
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequest.lessonName());

        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new NotFoundException(String.format("Course with id: %s not found", courseId)));
        lesson.setCourse(course);
        lessonRepo.save(lesson);
        log.info("Lesson successfully saved to course id: "+courseId);
        return LessonResponse.builder()
                .lessonName(lesson.getLessonName())
                .build();
    }

    @Override
    public List<LessonResponse> getAllLessons() {
        return lessonRepo.getAllLessons();
    }

    @Override
    public LessonResponse getLessonById(Long id) {
        return lessonRepo.getLessonById(id).orElseThrow(()->
                new NotFoundException(String.format("Lesson with id: %s not found",id)));
    }

    @Override
    public SimpleResponse updateLesson(Long id, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepo.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Lesson with id: %s not found", id)));
        lesson.setLessonName(lessonRequest.lessonName());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id: "+id+" successfully updated")
                .build();
    }

    @Override
    public SimpleResponse deleteLesson(Long id) {
       if (!lessonRepo.existsById(id)){
           throw new NotFoundException(String.format("Lesson with id: %s successfully deleted"));
       }
        lessonRepo.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Lesson with id: "+id+" is deleted")
                .build();
    }

    @Override
    public PaginationResponse getAllPagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<LessonResponse>lessons = lessonRepo.findAllLessons(pageable);
        return PaginationResponse.builder()
                .t(Collections.singletonList(lessons.getContent()))
                .currentPage(lessons.getNumber())
                .pageSize(lessons.getTotalPages())
                .build();
    }

}
