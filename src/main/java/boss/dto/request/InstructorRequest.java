package boss.dto.request;

import jakarta.validation.Valid;
import lombok.Builder;

@Builder
public record InstructorRequest(String firstName, String lastName, @Valid String phoneNumber, String specialization) {
}
