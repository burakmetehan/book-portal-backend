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

    private User findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User is not found!");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsersWithPagination(int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllByActiveTrueOrderByUsername(paged);
    }

    public User getUserById(long id) {
        return this.findById(id);
    }

    public Page<User> getUserById(long id, Pageable pageable) {
        return userRepository.findByIdAndActiveTrue(id, pageable);
    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("User is not found!");
        }
    }

    public Page<User> getAllUsersByUsernameWithPagination(String username, int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllByUsernameContainsIgnoreCaseAndActiveTrueOrderByUsername(username, paged);
    }

    public List<User> getUsersByUsernameStartsWith(String username) {
        return userRepository.findByUsernameStartsWithAndActiveTrueOrderByUsername(username);
    }

    public Page<User> getUsersByUsernameWithPagination(String name, int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return userRepository.findByUsernameStartsWithAndActiveTrueOrderByUsername(name, paged);
    }

    public User saveUser(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        // Every user has ROLE_USER by default
        Optional<Role> roleOptional = roleRepository.findByName(ROLE_USER);
        if (roleOptional.isPresent()) {
            user.setRoles(Set.of(roleOptional.get()));
            //user.addRole(roleOptional.get());
        } else {
            throw new RoleNotFoundException("Role is not found!");
        }

        return userRepository.save(user);
    }

    public User updateUser(long id, UserUpdateDTO userUpdateDTO) {
        User user = this.findById(id);
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()));
        return userRepository.save(user);
    }

    public User removeUser(long id) {
        User user = this.findById(id);
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    /*public List<UserResponseDTO> getAllUsers() { // can be directly get the
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userResponseDTOList.add(new UserResponseDTO(user));
        }

        return userResponseDTOList;
    }*/
}
