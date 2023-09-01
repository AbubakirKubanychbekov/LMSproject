package boss.dto.response;

import lombok.Builder;

@Builder
public record StudentResponse(Long id,String firstName,String lastName,String phoneNumber,String email,String studyFormat,boolean isBlock) {
    public StudentResponse {
    }
}
