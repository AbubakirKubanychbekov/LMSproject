package boss.dto.request;

import lombok.Builder;

@Builder
public record CompanyRequest(String name, String country, String address, String phoneNumber) {
    public CompanyRequest {
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String country() {
        return country;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String phoneNumber() {
        return phoneNumber;
    }
}
