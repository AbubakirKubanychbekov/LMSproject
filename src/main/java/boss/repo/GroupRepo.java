package boss.repo;

import boss.dto.response.GroupResponse;
import boss.entities.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group,Long> {

    @Query("SELECT new boss.dto.response.GroupResponse(g.id,g.groupName,g.imageLink,g.description) FROM Group g")
    List<GroupResponse> getAllGroups();

   Optional<GroupResponse> getGroupById(Long id);


   @Query("select new boss.dto.response.GroupResponse(g.id,g.groupName,g.imageLink,g.description) from Group g")
    Page<GroupResponse> findAllGroups(Pageable pageable);
}
