package boss.api;


import boss.dto.request.GroupRequest;
import boss.dto.response.GroupResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.GroupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
@Tag(name = "GroupApi")
public class GroupApi {

    private final GroupService groupService;


    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    @GetMapping
    public List<GroupResponse> getAllGroups(){
        return groupService.getAllGroups();
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PostMapping("/{courseId}")
    public GroupResponse save(@RequestBody GroupRequest groupRequest,
                              @PathVariable Long courseId){
        return groupService.save(groupRequest,courseId);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @GetMapping("/{id}")
    public GroupResponse getById(@PathVariable Long id){
        return groupService.getGroupById(id);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @PutMapping("/{id}")
    public SimpleResponse updateGroup(@PathVariable Long id,
                                      @RequestBody GroupRequest groupRequest){
        return groupService.updateGroup(id,groupRequest);
    }

    @PreAuthorize("hasAnyAuthority('INSTRUCTOR')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return groupService.delete(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PaginationResponse> paginationResponse(
            @RequestParam @Min(1) int currentPage,
            @RequestParam @Min(1) int pageSize
    ) {
        if (currentPage < 1 || pageSize < 1) {
            return ResponseEntity.badRequest().build();
        }

        PaginationResponse response = groupService.getAllPagination(currentPage, pageSize);
        return ResponseEntity.ok(response);
    }


}
