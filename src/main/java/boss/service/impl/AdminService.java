package boss.service.impl;


import boss.entities.User;
import boss.enums.Role;
import boss.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct
//    private void saveAdmin(){
//        User admin = User.builder()
//                .firstName("Admin")
//                .lastName("Admin")
//                .email("admin@gmail.com")
//                .role(Role.ADMIN)
//                .password(passwordEncoder.encode("admin"))
//                .build();
//        userRepo.save(admin);
//    }
}
