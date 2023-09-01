package boss.api;

import boss.dto.request.StudentRequest;
import boss.dto.response.StudentResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentApi {

    private final StudentService studentService;

    @GetMapping
    public List<StudentResponse> getAllStudents(){
        return studentService.getAllStudents();
    }


    @PostMapping("/{groupId}")
    public StudentResponse save(@PathVariable Long groupId,
                                @RequestBody StudentRequest studentRequest){
        return studentService.save(groupId,studentRequest);
    }

    @GetMapping("{id}")
    public StudentResponse getById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateStudent(@PathVariable Long id,
                                        @RequestBody StudentRequest studentRequest){
        return studentService.updateStudent(id,studentRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteStudent(@PathVariable Long id){
        return studentService.deleteStudent(id);
    }
}
