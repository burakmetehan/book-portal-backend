package tr.com.obss.jip.springfinal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.UserResponseDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    // findByUsernameStartsWithAndActiveTrueOrderByCreateDate
    List<User> findByUsernameStartsWithAndActiveTrueOrderByUsername(String username);
}
