package boss.service;

import boss.dto.request.AuthRequest;
import boss.dto.request.UserRequest;
import boss.dto.response.AuthenticationResponse;
import boss.dto.response.UserResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.security.Principal;

public interface UserService {
    void init();
    AuthenticationResponse signIn(AuthRequest signInRequest);

    UserResponse update(Principal principal, Long userId, UserRequest userRequest);

    SimpleResponse signUp(UserRequest userRequest);
}
