package boss.service;

import boss.dto.request.GroupRequest;
import boss.dto.response.GroupResponse;
import boss.dto.response.PaginationResponse;
import boss.dto.simpleResponse.SimpleResponse;

import java.util.List;

public interface GroupService {
    GroupResponse save(GroupRequest groupRequest, Long courseId);

    List<GroupResponse> getAllGroups();

    GroupResponse getGroupById(Long id);

    SimpleResponse updateGroup(Long id, GroupRequest groupRequest);

    SimpleResponse delete(Long id);

    PaginationResponse getAllPagination(int currentPage, int pageSize);
}
