package tr.com.obss.jip.springfinal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springfinal.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndActiveTrue(String username);

    Page<User> findByUsernameAndActiveTrue(String username, Pageable pageable);

    Optional<User> findByIdAndActiveTrue(long id);

    Page<User> findByIdAndActiveTrue(long id, Pageable pageable);

    List<User> findAllByUsernameContainsIgnoreCaseAndActiveTrueOrderByUsername(String username);

    Page<User> findAllByUsernameContainsIgnoreCaseAndActiveTrueOrderByUsername(String username, Pageable pageable);

    List<User> findAllByActiveTrueOrderByUsername();

    Page<User> findAllByActiveTrueOrderByUsername(Pageable pageable);

    boolean existsByUsername(String username);
}
