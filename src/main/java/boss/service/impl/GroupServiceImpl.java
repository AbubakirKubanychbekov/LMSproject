package boss.service.impl;

import boss.dto.request.GroupRequest;
import boss.dto.response.GroupResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;
import boss.entities.Course;
import boss.entities.Group;
import boss.exception.NotFoundException;
import boss.repo.CourseRepo;
import boss.repo.GroupRepo;
import boss.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final CourseRepo courseRepo;

    @Override
    public GroupResponse save(GroupRequest groupRequest, Long courseId) {
        Group group = new Group();
        group.setGroupName(groupRequest.groupName());
        group.setImageLink(groupRequest.imageLink());
        group.setDescription(groupRequest.description());
        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new NotFoundException(String.format("Course with id: %s not found", courseId)));
        course.addGroup(group);
        group.addCourse(course);
        groupRepo.save(group);
        log.info("Group successfully saved to course id: "+courseId);
        return GroupResponse.builder()
                .groupName(groupRequest.groupName())
                .imageLink(groupRequest.imageLink())
                .description(groupRequest.description())
                .build();
    }

    @Override
    public List<GroupResponse> getAllGroups() {
        return groupRepo.getAllGroups();
    }

    @Override
    public GroupResponse getGroupById(Long id) {
        return groupRepo.getGroupById(id).orElseThrow(()->
                new NotFoundException(String.format("Group with id: %s not found",id)));
    }

    @Override
    public SimpleResponse updateGroup(Long id, GroupRequest groupRequest) {
        Group group = groupRepo.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Group with id: %s not found", id)));
        group.setGroupName(groupRequest.groupName());
        group.setImageLink(groupRequest.imageLink());
        group.setDescription(groupRequest.description());
        groupRepo.save(group);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Group successfully updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
       if (!groupRepo.existsById(id)){
           throw new NotFoundException(
                   "Group with id: "+id+" not found");
       }
       groupRepo.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Group with id: %s successfully deleted",id))
                .build();
    }

    @Override
    public PaginationResponse getAllPagination(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1,pageSize);
        Page<GroupResponse>groups = groupRepo.findAllGroups(pageable);
        return PaginationResponse.builder()
                .t(Collections.singletonList(groups.getContent()))
                .currentPage(groups.getNumber())
                .pageSize(groups.getTotalPages())
                .build();
    }


}
