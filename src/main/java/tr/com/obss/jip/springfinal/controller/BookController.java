package tr.com.obss.jip.springfinal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.Book;
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


    /**
     * @return List of all the books
     */
    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getUser(@PathVariable(name = "bookId") long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping("/with-jpa-pagination")
    public ResponseEntity<Page<Book>> getUsersWithJpaPagination(
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(bookService.findAllWithJpaPagination(pageNumber, pageSize));
    }

    @GetMapping("/by-name")
    public ResponseEntity<Book> searchUser(@RequestParam(name = "name", defaultValue = "") String name) {
        return ResponseEntity.ok(bookService.findByBookName(name));
    }

    @GetMapping("/all-by-name")
    public ResponseEntity<List<Book>> searchAllUser(@RequestParam(name = "name", defaultValue = "") String name) {
        return ResponseEntity.ok(bookService.findAllByBookName(name));
    }

    /* ##### POST Mappings ##### */
    @PostMapping("")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.saveBook(bookDTO));
    }

    /* ##### PUT Mappings ##### */
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateUser(@PathVariable(name = "bookId") long id, @Valid @RequestBody
    BookUpdateDTO bookUpdateDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookUpdateDTO));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteUser(@PathVariable(name = "bookId") long id) {
        return ResponseEntity.ok(bookService.removeBook(id));
    }
}
