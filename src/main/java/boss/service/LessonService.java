package boss.service;

import boss.dto.request.LessonRequest;
import boss.dto.response.LessonResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface LessonService {
    LessonResponse saveLesson(Long courseId, LessonRequest lessonRequest);

    List<LessonResponse> getAllLessons();

    LessonResponse getLessonById(Long id);

    SimpleResponse updateLesson(Long id, LessonRequest lessonRequest);

    SimpleResponse deleteLesson(Long id);
}
