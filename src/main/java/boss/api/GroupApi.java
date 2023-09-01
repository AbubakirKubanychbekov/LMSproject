package boss.api;


import boss.dto.request.GroupRequest;
import boss.dto.response.GroupResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupApi {

    private final GroupService groupService;


    @GetMapping
    public List<GroupResponse> getAllGroups(){
        return groupService.getAllGroups();
    }

    @PostMapping("/{courseId}")
    public GroupResponse save(@RequestBody GroupRequest groupRequest,
                              @PathVariable Long courseId){
        return groupService.save(groupRequest,courseId);
    }

    @GetMapping("/{id}")
    public GroupResponse getById(@PathVariable Long id){
        return groupService.getGroupById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse updateGroup(@PathVariable Long id,
                                      @RequestBody GroupRequest groupRequest){
        return groupService.updateGroup(id,groupRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return groupService.delete(id);
    }

}
