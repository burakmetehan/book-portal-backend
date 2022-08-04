package tr.com.obss.jip.springfinal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.exception.BookNotFoundException;
import tr.com.obss.jip.springfinal.model.BookDTO;
import tr.com.obss.jip.springfinal.model.BookUpdateDTO;
import tr.com.obss.jip.springfinal.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /* ##### GET Mappings ##### */
    @GetMapping("")
    public ResponseEntity<Page<Book>> searchAllBooksWithPagination(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(bookService.getAllBooksWithPagination(pageNumber, pageSize));
    }

    /**
     * @param id Take an id from request parameters, namely id.
     * @return The book with id. If it is not found, Error 500 is thrown
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> searchBookById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

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

    @GetMapping("/no-pagination/name/{book_name}")
    public ResponseEntity<List<Book>> searchBooksByNameWithoutPagination(@PathVariable(name = "book_name") String name) {
        return ResponseEntity.ok(bookService.getAllBooksByName(name));
    }


    /* ##### POST Mappings ##### */
    @PostMapping("")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.saveBook(bookDTO));
    }

    /* ##### PUT Mappings ##### */
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateUser(
            @PathVariable(name = "bookId") long id, @Valid @RequestBody BookUpdateDTO bookUpdateDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookUpdateDTO));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteUser(@PathVariable(name = "bookId") long id) {
        return ResponseEntity.ok(bookService.removeBook(id));
    }
}
