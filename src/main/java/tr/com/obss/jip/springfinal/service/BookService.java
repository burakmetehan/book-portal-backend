package tr.com.obss.jip.springfinal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.model.BookDTO;
import tr.com.obss.jip.springfinal.model.BookUpdateDTO;
import tr.com.obss.jip.springfinal.repo.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);

        return book;
    }

    public Book findById(long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new IllegalArgumentException("Book not found");
        }
    }

    public Book getById(long id) {
        return this.findById(id);
    }

    /**
     *
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(long id, BookUpdateDTO bookDTO) {
        Book book = this.findById(id);

        String author = bookDTO.getAuthor();
        Integer pageCount = book.getPageCount();

        if (author != null && !author.isEmpty()) {
            book.setAuthor(author);
        }

        if (pageCount != null) {
            book.setPageCount(pageCount);
        }

        book.setPublisher(bookDTO.getPublisher());

        return bookRepository.save(book);
    }

    public Book removeBook(long id) {
        Book book = this.findById(id);
        book.setActive(false);
        return bookRepository.save(book);
    }

    public Page<Book> findAllWithJpaPagination(int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(paged);
    }

    public Book findByBookName(String name) {
        Optional<Book> bookOptional = bookRepository.findByName(name);

        return bookOptional.orElseThrow(() -> {
            throw new IllegalArgumentException("Book not found");
        });
    }

    public List<Book> findAllByBookName(String name) {
        return bookRepository.findAllByNameAndActiveTrueOrderByName(name);
    }


}
