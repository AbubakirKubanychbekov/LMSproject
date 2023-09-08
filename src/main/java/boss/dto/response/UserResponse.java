package boss.dto.response;

import boss.entities.User;
import boss.enums.Role;

public record UserResponse(

        Long id,
        String firstName,
        String lastName,
        String email,
        Role role

) {

    public static UserResponse build(User user){
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

}
