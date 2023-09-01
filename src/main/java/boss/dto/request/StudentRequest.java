package boss.dto.request;

import lombok.Builder;

@Builder
public record StudentRequest(String firstName,String lastName,String phoneNumber,String email,String studyFormat) {
}
