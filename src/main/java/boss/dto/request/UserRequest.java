package boss.dto.request;

import boss.entities.User;
import boss.enums.Role;
import boss.validation.PhoneNumberValidation;

public record UserRequest(

        String firstName,

        String lastName,

        String email,
        String password,
        Role role,

        @PhoneNumberValidation
        String phoneNumber,
        String studyFormat,

        String specialization

) {

    public User build(){
        return User.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
