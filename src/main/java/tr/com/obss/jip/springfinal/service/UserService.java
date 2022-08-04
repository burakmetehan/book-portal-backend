package tr.com.obss.jip.springfinal.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.Role;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.UserDTO;
import tr.com.obss.jip.springfinal.model.UserResponseDTO;
import tr.com.obss.jip.springfinal.repo.BookRepository;
import tr.com.obss.jip.springfinal.repo.RoleRepository;
import tr.com.obss.jip.springfinal.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    public static final String ROLE_USER = "ROLE_USER";

    private final BookRepository bookRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(
            BookRepository bookRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder encoder) {
        this.bookRepository = bookRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User save(UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));

        Optional<Role> userOptional = roleRepository.findByName(ROLE_USER);
        userOptional.ifPresent(role -> user.setRoles(Set.of(role)));

        return userRepository.save(user);
    }

    public UserResponseDTO findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new UserResponseDTO(userOptional.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public List<UserResponseDTO> getAllUsers() { // can be directly get the
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for ( User user : userRepository.findAll() ) {
            userResponseDTOList.add(new UserResponseDTO(user));
        }

        return userResponseDTOList;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /*

    public List<User> getUsersWithRole(List<String> roles) {
        return userRepository.findByRoles_NameIn(roles);
    }

    public User findById(long id) {
        Optional<User> optional = userRepository.findById(id);
        *//*
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new IllegalArgumentException("User not found");
        }
        *//*
        return optional.orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    public User update(long id, UserUpdateDTO userUpdateDTO) {
        User user = this.findById(id);
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()));
        return userRepository.save(user);
    }

    public User remove(long id) {
        User user = this.findById(id);
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> optional = userRepository.findByUsername(username);

        return optional.orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    public List<User> findAllByUsername(String username) {
        return userRepository.findByUsernameStartsWithAndActiveTrueOrderByCreateDate(username);
    }

    public User findByIdHql(long id) {
        Optional<User> optional = userRepository.getById(id);

        return optional.orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    public User findByIdNative(long id) {
        Optional<User> optional = userRepository.getByIdNative(id);

        return optional.orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    public Page<User> findAllWithJpaPagination(int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(paged);
    }

    public List<User> findAllWithDaoPagination(int pageNumber, int pageSize) {
        return userDAO.get(pageNumber, pageSize);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByUsername(username);
        return new MyUserDetails(user);
    }*/

}
