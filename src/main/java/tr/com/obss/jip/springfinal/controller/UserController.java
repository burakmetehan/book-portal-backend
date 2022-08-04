package tr.com.obss.jip.springfinal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.UserDTO;
import tr.com.obss.jip.springfinal.model.UserResponseDTO;
import tr.com.obss.jip.springfinal.model.UserUpdateDTO;
import tr.com.obss.jip.springfinal.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("")
    public ResponseEntity<String> get(@RequestParam(defaultValue = "World") String name) {
        return ResponseEntity.ok("Hello " + name + "!");
    }

    /**
     *
     * @return List of all the users
     */
    @GetMapping("/all-users")
//    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable(name = "userId") long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/with-jpa-pagination")
    public ResponseEntity<Page<User>> getUsersWithJpaPagination(
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(userService.findAllWithJpaPagination(pageNumber, pageSize));
    }

    @GetMapping("/by-username")
    public ResponseEntity<User> searchUser(@RequestParam(name = "username", defaultValue = "") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/all-by-username")
    public ResponseEntity<List<User>> searchAllUser(@RequestParam(name = "username", defaultValue = "") String username) {
        return ResponseEntity.ok(userService.findAllByUsername(username));
    }

    /* ##### POST Mappings ##### */
    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.save(userDTO));
    }

    /* ##### PUT Mappings ##### */
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "userId") long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable(name = "userId") long id) {
        return ResponseEntity.ok(userService.removeBook(id));
    }
}
