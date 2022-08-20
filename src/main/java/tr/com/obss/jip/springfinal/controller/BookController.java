package tr.com.obss.jip.springfinal.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.model.BookDTO;
import tr.com.obss.jip.springfinal.model.BookUpdateDTO;
import tr.com.obss.jip.springfinal.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /* ##### GET Mappings ##### */

    /**
     * Can be used to search all books
     * @param pageNumber non-negative integer
     * @param pageSize non-negative integer
     * @return The page of the books
     */
    @GetMapping("")
    public ResponseEntity<Page<Book>> searchAllBooksWithPagination(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(bookService.getAllBooksWithPagination(pageNumber, pageSize));
    }

    /**
     * Can be used to search a book by id
     * @param id Take an id from request parameters, namely id.
     * @return The book with id. If it is not found, Error 500 is thrown
     */
    @GetMapping("/{id}")
    public ResponseEntity<Page<Book>> searchBookById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(bookService.getBookById(id, PageRequest.of(0, 1))); // Only one user. Page is needed for frontend
    }

    /**
     * Can be used to search a book by name
     * @param name name of the book
     * @param pageNumber non-negative integer
     * @param pageSize non-negative integer
     * @return The page of the books
     */
    @GetMapping("/name/{book_name}")
    public ResponseEntity<Page<Book>> searchBooksByName(
            @PathVariable(name = "book_name") String name,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(bookService.getAllBooksByNameWithPagination(name, pageNumber, pageSize));
    }

    /**
     * @return List of all the books
     */
    @GetMapping("/no-pagination")
    public ResponseEntity<List<Book>> searchAllBooksWithoutPagination() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/no-pagination/{book_name}")
    public ResponseEntity<List<Book>> searchBooksByNameWithoutPagination(@PathVariable(name = "book_name") String name) {
        return ResponseEntity.ok(bookService.getAllBooksByName(name));
    }


    /* ##### POST Mappings ##### */

    /**
     * Can be used to create/add book
     * @param bookDTO model that consist of `name`, `author`, `pageCount`, `type`, `publisher` and `publicationDate`
     * @return The book that is added
     */
    @PostMapping("")
    @Secured("ROLE_ADMIN") // Only admins can add books
    public ResponseEntity<Book> createBook(@Valid @RequestBody @DateTimeFormat BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.saveBook(bookDTO));
    }

    /* ##### PUT Mappings ##### */

    /**
     * Can be used to update the book
     * @param id the id of the book
     * @param bookUpdateDTO model that consist of `name`, `author`, `pageCount`, `publisher` and `publicationDate`
     * @return The updated book
     */
    @PutMapping("/{bookId}")
    @Secured("ROLE_ADMIN") // Only admins can update books
    public ResponseEntity<Book> updateBook(
            @PathVariable(name = "bookId") long id, @Valid @RequestBody @DateTimeFormat BookUpdateDTO bookUpdateDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookUpdateDTO));
    }

    /* ##### DELETE Mappings ##### */

    /**
     * Can be used to delete the book
     * @param id the id of the book
     * @return The deleted book
     */
    @DeleteMapping("/{bookId}")
    @Secured("ROLE_ADMIN") // Only admins can delete books
    public ResponseEntity<Book> removeBook(@PathVariable(name = "bookId") long id) {
        return ResponseEntity.ok(bookService.removeBook(id));
    }
}
