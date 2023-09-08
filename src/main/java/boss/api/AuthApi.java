package boss.api;

import boss.dto.request.AuthRequest;
import boss.dto.response.AuthenticationResponse;
import boss.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "AuthApi")
public class AuthApi {

    private final UserService userService;

    @PostMapping("/signIn")
    @Operation(summary = "Sign In",description = "To sign in fill all the fields!")
    AuthenticationResponse signIn(@RequestBody AuthRequest signInRequest){
        return userService.signIn(signInRequest);
    }

}
