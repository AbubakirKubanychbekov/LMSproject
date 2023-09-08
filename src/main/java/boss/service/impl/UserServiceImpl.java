package boss.service.impl;


import boss.dto.request.AuthRequest;
import boss.dto.request.UserRequest;
import boss.dto.response.AuthenticationResponse;
import boss.dto.response.UserResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Instructor;
import boss.entities.Student;
import boss.entities.User;
import boss.enums.Role;
import boss.exception.NotFoundException;
import boss.repo.InstructorRepo;
import boss.repo.StudentRepo;
import boss.repo.UserRepo;
import boss.security.JwtService;
import boss.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final InstructorRepo instructorRepo;
    private final StudentRepo studentRepo;

    @PostConstruct
    @Override
    public void init() {
        User user = new User();
        user.setRole(Role.ADMIN);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            userRepository.save(user);
        }
    }
    @Override
    public AuthenticationResponse signIn(AuthRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() ->
                new EntityNotFoundException("User with email: " + signInRequest.email() + " not found"));

        if (signInRequest.email().isBlank()) {
            throw new BadCredentialsException("Email is blank");
        }
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password!");
        }
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .build();
    }




    @Override
    public SimpleResponse signUp(UserRequest request) {
        if (request.role().equals(Role.INSTRUCTOR)) {
            Instructor instructor = convertRequestToInstructor(request);
            User user = convertRequestToUser(request);
            instructor.setUser(user);
            user.setInstructor(instructor);
            instructorRepo.save(instructor);
            userRepository.save(user);
            log.info("Successfully saved instructor with id: " + instructor.getId());
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully saved instructor with id: " + instructor.getId())
                    .build();
        } else if (request.role().equals(Role.STUDENT)) {
            Student student = convertRequestToStudent(request);
            User user = convertRequestToUser(request);
            student.setUser(user);
            user.setStudent(student);
            studentRepo.save(student);
            userRepository.save(user);
            log.info("Successfully saved instructor with id: " + student.getId());
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Successfully saved instructor with id: " + student.getId())
                    .build();
        } else {
            log.info("Role is invalid!");
            throw new NotFoundException("Role is invalid!");
        }
    }
    private Student convertRequestToStudent(UserRequest request) {
        Student student = new Student();
        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setStudyFormat(request.studyFormat());
        student.setBlock(false);
        return student;
    }


    private Instructor convertRequestToInstructor(UserRequest request) {
        Instructor instructor = new Instructor();
        instructor.setFirstName(request.firstName());
        instructor.setLastName(request.lastName());
        instructor.setPhoneNumber(request.phoneNumber());
        instructor.setSpecialization(request.specialization());
        return instructor;
    }
    private User convertRequestToUser(UserRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setPassword(passwordEncoder.encode(request.password()));
        return user;
    }



    @Override
    @Transactional
    public UserResponse update(Principal principal, Long userId, UserRequest userRequest) {
        User userForResponse = new User();
        String email = principal.getName();
        User authUser = userRepository.getUserByEmail(email).orElseThrow(() ->
                new RuntimeException("User with email: " + email + " not found!"));
        if (authUser.getRole().equals(Role.ADMIN)){
            userForResponse = update(userRequest, userId);
        }
        else if (authUser.getRole().equals(Role.USER)){
            if (authUser.getId().equals(userId)){
                userForResponse =  update(userRequest, userId);
            }else {
                throw new RuntimeException("Sen bashka userdin info. ozgortkonu araket kylyp jatasyn!!!");
            }
        }
        return new UserResponse(
                userForResponse.getId(),
                userForResponse.getFirstName(),
                userForResponse.getLastName(),
                userForResponse.getEmail(),
                userForResponse.getRole());
    }



    private User update(UserRequest userRequest, Long userId){
        User newUser = userRequest.build();

        User parUser = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User with id: " + userId + " not found!"));
        parUser.setFirstName(newUser.getFirstName());
        parUser.setLastName(newUser.getLastName());
        parUser.setEmail(newUser.getEmail());

        return newUser;
    }
}
