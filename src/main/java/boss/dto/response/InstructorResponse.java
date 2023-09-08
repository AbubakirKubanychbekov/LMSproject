package boss.dto.response;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
public record InstructorResponse(Long id, String firstName, String lastName, @Valid String phoneNumber, String specialization) {
    public InstructorResponse {
    }
}
