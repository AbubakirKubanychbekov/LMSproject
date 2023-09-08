package boss.dto.response;

import boss.enums.Role;

public record UserResponseWithToken(
        Long id,
        String email,
        Role role,
        String token
) {
}
