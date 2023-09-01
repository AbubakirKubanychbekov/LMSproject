package boss.dto.response;

import lombok.Builder;

@Builder
public record CompanyResponse(Long id, String name, String country, String address, String phoneNumber) {
    public CompanyResponse {
    }


}
