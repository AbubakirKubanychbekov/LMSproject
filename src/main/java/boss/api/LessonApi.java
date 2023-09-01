package boss.api;

import boss.dto.request.LessonRequest;
import boss.dto.response.LessonResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonApi {

    private final LessonService lessonService;

    @GetMapping
    public List<LessonResponse> getAllLessons(){
        return lessonService.getAllLessons();
    }


    @PostMapping("/{courseId}")
    public LessonResponse save(@PathVariable Long courseId,
                               @RequestBody LessonRequest lessonRequest){
        return lessonService.saveLesson(courseId,lessonRequest);
    }

    @GetMapping("/{id}")
    public LessonResponse getById(@PathVariable Long id){
        return lessonService.getLessonById(id);
    }

    @PutMapping("{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody LessonRequest lessonRequest){
        return lessonService.updateLesson(id,lessonRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteLesson(@PathVariable Long id){
        return lessonService.deleteLesson(id);
    }
}
