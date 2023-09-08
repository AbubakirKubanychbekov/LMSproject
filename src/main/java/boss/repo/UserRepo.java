package boss.repo;

import boss.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> getUserByEmail(String email);
    Boolean existsByEmail(String email);
}
