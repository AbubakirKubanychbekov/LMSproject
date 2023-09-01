package boss.dto.request;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseRequest(String courseName, LocalDate dateOfStart, String description) {
    public CourseRequest {
    }
}
