package tr.com.obss.jip.springfinal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springfinal.entity.Book;
import tr.com.obss.jip.springfinal.entity.Role;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.MyUserDetails;
import tr.com.obss.jip.springfinal.model.UserDTO;
import tr.com.obss.jip.springfinal.model.UserResponseDTO;
import tr.com.obss.jip.springfinal.model.UserUpdateDTO;
import tr.com.obss.jip.springfinal.repo.BookRepository;
import tr.com.obss.jip.springfinal.repo.RoleRepository;
import tr.com.obss.jip.springfinal.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    public static final String ROLE_USER = "ROLE_USER";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(
            BookRepository bookRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder encoder) {
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

    public User findById(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public UserResponseDTO getById(long id) {
        return new UserResponseDTO(findById(id));
    }

    public List<UserResponseDTO> getAllUsers() { // can be directly get the
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for ( User user : userRepository.findAll() ) {
            userResponseDTOList.add(new UserResponseDTO(user));
        }

        return userResponseDTOList;
    }

    public User updateUser(long id, UserUpdateDTO userUpdateDTO) {
        User user = this.findById(id);
        user.setPassword(encoder.encode(userUpdateDTO.getPassword()));
        return userRepository.save(user);
    }

    public User removeBook(long id) {
        User user = this.findById(id);
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }

    public Page<User> findAllWithJpaPagination(int pageNumber, int pageSize) {
        var paged = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(paged);
    }

    public User findByUsername(String username) {
        Optional<User> optional = userRepository.findByUsername(username);

        return optional.orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    public List<User> findAllByUsername(String username) {
        return userRepository.findByUsernameStartsWithAndActiveTrueOrderByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByUsername(username);
        return new MyUserDetails(user);
    }


    /*

    public List<User> getUsersWithRole(List<String> roles) {
        return userRepository.findByRoles_NameIn(roles);
    }*/

}