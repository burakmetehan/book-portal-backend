package tr.com.obss.jip.springfinal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.exception.BookNotFoundException;
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

    /**
     *
     * @param id ID of book to search
     * @return {@link Book Book} or throw {@link BookNotFoundException BookNotFoundException}
     */
    private Book findById(long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        } else {
            throw new BookNotFoundException("Book is not found!");
        }
    }

    /**
     *
     * @return List of all books
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     *
     * @param pageNumber Zero-based page index, must not be negative
     * @param pageSize The size of the page to be returned, must be greater than 0
     * @return Page of all books
     */
    public Page<Book> getAllBooksWithPagination(int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAll(paged);
    }

    /**
     *
     * @param id ID of the book which is going to be searched
     * @return @return {@link Book Book} if exists
     */
    public Book getBookById(long id) {
        return findById(id);
    }

    /**
     *
     * @param name Name of the searched book
     * @return List of books if exists
     */
    public List<Book> getAllBooksByName(String name) {
        return bookRepository.findAllByNameAndActiveTrueOrderByName(name);
    }

    /**
     *
     * @param name Name of the searched book
     * @param pageNumber Zero-based page index, must not be negative
     * @param pageSize The size of the page to be returned, must be greater than 0.
     * @return Page of books if exists
     */
    public Page<Book> getAllBooksByNameWithPagination(String name, int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllByNameAndActiveTrueOrderByName(name, paged);
    }

    public Book saveBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        return bookRepository.save(book);
    }

    public Book updateBook(long id, BookUpdateDTO bookDTO) {
        Book book = this.findById(id);

        String author = bookDTO.getAuthor();
        int pageCount = book.getPageCount();

        if (author != null && !author.isEmpty()) {
            book.setAuthor(author);
        }

        book.setPageCount(pageCount);
        book.setPublisher(bookDTO.getPublisher());

        return bookRepository.save(book);
    }

    public Book removeBook(long id) {
        Book book = this.findById(id);
        book.setActive(false);
        return bookRepository.save(book);
    }
}
