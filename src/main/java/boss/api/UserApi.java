package boss.api;

import boss.dto.request.UserRequest;
import boss.dto.response.UserResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "UserApi")
public class UserApi {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Sign Up",description = "To sign up fill all the fields!")
    public ResponseEntity<SimpleResponse> signUp(@RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.ok(userService.signUp(userRequest));
    }

    @PutMapping("/{userId}")
    @Secured({"USER", "ADMIN"})
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId,
                                                   @RequestBody @Valid UserRequest userRequest,
                                                   Principal principal) {
        return ResponseEntity.ok(userService.update(principal, userId, userRequest));

    }

}
