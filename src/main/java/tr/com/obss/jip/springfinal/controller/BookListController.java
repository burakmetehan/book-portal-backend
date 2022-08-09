package tr.com.obss.jip.springfinal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.service.AddRemoveService;

@RestController
@RequestMapping("/ar")
@CrossOrigin(origins = "*")
public class BookListController {
    private final AddRemoveService addRemoveService;

    public BookListController(AddRemoveService addRemoveService) {
        this.addRemoveService = addRemoveService;
    }

    @PutMapping("/a/readList")
    public ResponseEntity<User> addBookToReadList(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "bookId") long bookId) {
        return ResponseEntity.ok(addRemoveService.addBookToReadList(userId, bookId));
    }

    @PutMapping("/r/readList")
    public ResponseEntity<?> removeBookFromReadList(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "bookId") long bookId) {
        return ResponseEntity.ok(addRemoveService.removeBookFromReadList(userId, bookId));
    }

    @PutMapping("/a/favoriteList")
    public ResponseEntity<User> addBookToFavoriteList(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "bookId") long bookId) {
        return ResponseEntity.ok(addRemoveService.addBookToFavoriteList(userId, bookId));
    }

    @PutMapping("/r/favoriteList")
    public ResponseEntity<?> removeBookFromFavoriteList(
            @RequestParam(name = "userId") long userId,
            @RequestParam(name = "bookId") long bookId) {
        return ResponseEntity.ok(addRemoveService.removeBookFromFavoriteList(userId, bookId));
    }

}
