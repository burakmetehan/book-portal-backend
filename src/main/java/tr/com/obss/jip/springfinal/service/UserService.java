package tr.com.obss.jip.springfinal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Role;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.exception.RoleNotFoundException;
import tr.com.obss.jip.springfinal.exception.UserNotFoundException;
import tr.com.obss.jip.springfinal.model.UserDTO;
import tr.com.obss.jip.springfinal.model.UserUpdateDTO;
import tr.com.obss.jip.springfinal.repo.RoleRepository;
import tr.com.obss.jip.springfinal.repo.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    public static final String ROLE_USER = "ROLE_USER";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(
            RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * @param id ID of user to search
     * @return {@link User User} or throw {@link UserNotFoundException UserNotFoundException}
     */
    private User findById(long id) {
        Optional<User> userOptional = userRepository.findByIdAndActiveTrue(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User is not found!");
        }
    }

    /**
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAllByActiveTrueOrderByUsername();
    }

    /**
     * @param pageNumber Zero-based page index, must not be negative
     * @param pageSize   The size of the page to be returned, must be greater than 0
     * @return Page of all user
     */
    public Page<User> getAllUsersWithPagination(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllByActiveTrueOrderByUsername(pageRequest);
    }

    /**
     * @param id ID of the user which is going to be searched
     * @return {@link User User} if exists
     */
    public User getUserById(long id) {
        return this.findById(id);
    }

    /**
     * @param id       ID of the user which is going to be searched
     * @param pageable {@link Pageable Pageable} object
     * @return Page of user.
     */
    public Page<User> getUserByIdWithPagination(long id, Pageable pageable) {
        return userRepository.findByIdAndActiveTrue(id, pageable);
    }

    /**
     * @param username Username of the searched user
     * @return {@link User User} object
     */
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsernameAndActiveTrue(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("User is not found!");
        }
    }

    /**
     * @param username Username of the searched user
     * @return List of users
     */
    public List<User> getAllUsersByUsername(String username) {
        return userRepository.findAllByUsernameContainsIgnoreCaseAndActiveTrueOrderByUsername(username);
    }

    /**
     * @param username   Username of the searched user
     * @param pageNumber Zero-based page index, must not be negative
     * @param pageSize   The size of the page to be returned, must be greater than 0.
     * @return Page of users if exists
     */
    public Page<User> getAllUsersByUsernameWithPagination(String username, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllByUsernameContainsIgnoreCaseAndActiveTrueOrderByUsername(username, pageRequest);
    }

    /**
     * Adding user to database
     *
     * @param userDTO includes username and password
     * @return {@link User User} object that is added.
     */
    public User saveUser(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        // Every user has ROLE_USER by default
        Optional<Role> roleOptional = roleRepository.findByNameAndActiveTrue(ROLE_USER);
        if (roleOptional.isPresent()) {
            user.setRoles(Set.of(roleOptional.get()));
        } else {
            throw new RoleNotFoundException("Role is not found!");
        }

        return userRepository.save(user);
    }

    /**
     * Update the password of user
     *
     * @param id            ID of the user
     * @param userUpdateDTO includes password.
     * @return {@link User User} object that is updated.
     */
    public User updateUser(long id, UserUpdateDTO userUpdateDTO) {
        User user = this.findById(id);
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Soft delete of user
     *
     * @param id ID of user
     * @return {@link User User} object that is removed.
     */
    public User removeUser(long id) {
        User user = this.findById(id);
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    /**
     * Hard delete of user.
     *
     * @param id ID of the user.
     */
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
