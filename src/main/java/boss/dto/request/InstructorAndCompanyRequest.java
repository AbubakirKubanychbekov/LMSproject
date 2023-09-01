package boss.dto.request;


import lombok.Builder;

@Builder
public class InstructorAndCompanyRequest {
    private InstructorRequest instructorRequest;
    private Long companyId;
}
