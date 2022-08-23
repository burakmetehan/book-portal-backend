package tr.com.obss.jip.springfinal.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springfinal.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByNameAndActiveTrueOrderByName(String name);

    Page<Book> findAllByNameAndActiveTrueOrderByName(String name, Pageable pageable);

    Optional<Book> findByIdAndActiveTrue(long id);

    Page<Book> findByIdAndActiveTrue(long id, Pageable pageable);

    List<Book> findAllByActiveTrueOrderByName();

    Page<Book> findAllByActiveTrueOrderByName(Pageable pageable);

    Page<Book> findAllByNameContainsIgnoreCaseAndActiveTrueOrderByName(String name, Pageable pageable);
}
