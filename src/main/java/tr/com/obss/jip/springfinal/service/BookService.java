package tr.com.obss.jip.springfinal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.exception.BookNotFoundException;
import tr.com.obss.jip.springfinal.model.BookDTO;
import tr.com.obss.jip.springfinal.model.BookUpdateDTO;
import tr.com.obss.jip.springfinal.repo.BookRepository;

import java.util.Date;
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
        Optional<Book> bookOptional = bookRepository.findByIdAndActiveTrue(id);
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
        return bookRepository.findAllByActiveTrueOrderByName();
    }

    /**
     *
     * @param pageNumber Zero-based page index, must not be negative
     * @param pageSize The size of the page to be returned, must be greater than 0
     * @return Page of all books
     */
    public Page<Book> getAllBooksWithPagination(int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return bookRepository.findAllByActiveTrueOrderByName(paged);
    }

    /**
     *
     * @param id ID of the book which is going to be searched
     * @return @return {@link Book Book} if exists
     */
    public Book getBookById(long id) {
        return this.findById(id);
    }

    /**
     *
     * @param id ID of the book which is going to be searched
     * @param pageable pageable for one entry with Page Size: 1, Page Number: 0
     * @return Page of book that consist of only one book at most
     */
    public Page<Book> getBookById(long id, Pageable pageable) {
        return bookRepository.findByIdAndActiveTrue(id, pageable);
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
        return bookRepository.findAllByNameContainsIgnoreCaseAndActiveTrueOrderByName(name, paged);
    }

    /**
     * Creating a book in database
     * @param bookDTO
     * @return
     */
    public Book saveBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        return bookRepository.save(book);
    }

    /** @TODO Change it to make it return empty object or false when there is a missing field.
     * Updating a book in database
     * @param id
     * @param bookDTO
     * @return {@link Book Book} Object if it is successful; otherwise,
     */
    public Book updateBook(long id, BookUpdateDTO bookDTO) {
        Book book = this.findById(id);

        int newPageCount = bookDTO.getPageCount();
        String newPublisher = bookDTO.getPublisher();
        Date newPublicationDate = bookDTO.getPublicationDate();

        if (newPageCount > 0 && newPageCount < Integer.MAX_VALUE) {
            book.setPageCount(newPageCount);
        }

        if (newPublisher != null && !newPublisher.isEmpty()) {
            book.setPublisher(newPublisher);
        }

        if (newPublicationDate != null) {
            book.setPublicationDate(newPublicationDate);
        }

        return bookRepository.save(book);
    }

    public Book removeBook(long id) {
        Book book = this.findById(id);
        book.setActive(false);
        return bookRepository.save(book);
    }

    // @TODO Add hard delete/remove and name containing search
}
