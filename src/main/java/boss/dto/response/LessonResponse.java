package boss.dto.response;

import lombok.Builder;

@Builder
public record LessonResponse(Long id, String lessonName) {

    public LessonResponse {
    }
}
