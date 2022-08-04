package tr.com.obss.jip.springfinal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.springfinal.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);
}
