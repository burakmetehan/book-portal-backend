package tr.com.obss.jip.springfinal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.UserDTO;
import tr.com.obss.jip.springfinal.model.UserResponseDTO;
import tr.com.obss.jip.springfinal.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ##### GET Mappings ##### */
    @GetMapping("")
    public ResponseEntity<String> get(@RequestParam String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    /**
     *
     * @return List of all the users
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(userService.getAllBooks());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable(name = "userId") long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /* ##### POST Mappings ##### */
    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.save(userDTO));
    }

    /* ##### PUT Mappings ##### */

    /* ##### PATCH Mappings ##### */

    /* ##### DELETE Mappings ##### */
}
