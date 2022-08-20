package tr.com.obss.jip.springfinal.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.UserDTO;
import tr.com.obss.jip.springfinal.model.UserUpdateDTO;
import tr.com.obss.jip.springfinal.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ##### GET Mappings ##### */

    /**
     *
     * @param pageNumber non-negative integer, default is 0
     * @param pageSize non-negative integer, default is 10
     * @return Page of the users according to given parameters
     */
    @GetMapping("/")
    @Secured("ROLE_ADMIN") // Only admins can search users
    public ResponseEntity<Page<User>> searchAllUsers(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.getAllUsersWithPagination(pageNumber, pageSize));
    }

    /**
     *
     * @param id id of the user
     * @return user if the user exist
     */
    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN") // Only admins can search users
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     *
     * @param username username of the user
     * @param pageNumber non-negative integer, default is 0
     * @param pageSize non-negative integer, default is 10
     * @return Page of the users according to given parameters
     */
    @GetMapping("/name/{username}")
    @Secured("ROLE_ADMIN") // Only admins can search users
    public ResponseEntity<User> searchUserByUsername(
            @PathVariable(name = "username") String username,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/name/sw-{username}")
    @Secured("ROLE_ADMIN") // Only admins can search users
    public ResponseEntity<Page<User>> searchUsersByUsernameWithPagination(
            @PathVariable(name = "username") String username,
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.getUsersByUsernameWithPagination(username, pageNumber, pageSize));
    }

    @GetMapping("/no-pagination")
    @Secured("ROLE_ADMIN") // Only admins can search users
    public ResponseEntity<List<User>> searchAllUsersWithoutPagination() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/no-pagination/sw-{username}")
    @Secured("ROLE_ADMIN") // Only admins can search users
    public ResponseEntity<List<User>> searchUserByUsernameStartsWith(@PathVariable(name = "username") String name) {
        return ResponseEntity.ok(userService.getUsersByUsernameStartsWith(name));
    }

    /* ##### POST Mappings ##### */
    @PostMapping("")
    @Secured("ROLE_ADMIN") // Only admins can add users
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

   /* ##### PUT Mappings ##### */
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN") // Only admins can update users
    public ResponseEntity<User> updateUser(
            @PathVariable(name = "id") long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
    }

    /* ##### DELETE Mappings ##### */
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN") // Only admins can delete users
    public ResponseEntity<User> removeUser(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(userService.removeUser(id));
    }
}
