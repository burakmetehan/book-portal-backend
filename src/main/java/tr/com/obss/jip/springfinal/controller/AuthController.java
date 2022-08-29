package tr.com.obss.jip.springfinal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.config.JwtTokenUtil;
import tr.com.obss.jip.springfinal.entity.Role;
import tr.com.obss.jip.springfinal.entity.User;
import tr.com.obss.jip.springfinal.model.*;
import tr.com.obss.jip.springfinal.repo.UserRepository;
import tr.com.obss.jip.springfinal.service.JwtUserDetailsService;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService userDetailsService,
            UserRepository userRepository,
            JwtUserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    /**
     * Checking the JWT token is valid.
     *
     * @param authDTO {@code AuthDTO AuthDTO} object inside RequestBody
     * @return {@code AuthResponse AuthResponse} object
     */
    @PostMapping("")
    public ResponseEntity<AuthResponse> checkAuthenticationToken(@RequestBody AuthDTO authDTO) {
        String token = authDTO.getToken();
        String username = authDTO.getUsername();

        try {
            if (token == null || token.isEmpty() || username == null || username.isEmpty()) {
                return ResponseEntity.badRequest().body(new AuthResponse(false));
            }

            token = token.substring(7);
            if (token.isEmpty()) { // no proper token
                return ResponseEntity.badRequest().body(new AuthResponse(false));
            }

            String tokenUsername = jwtTokenUtil.getUsernameFromToken(token);
            if (!username.equals(tokenUsername)) { // username does not match
                return ResponseEntity.badRequest().body(new AuthResponse(false));
            }

            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(tokenUsername);
            Boolean isValid = jwtTokenUtil.validateToken(token, userDetails);

            if (isValid) {

                // Checking whether admin or not
                boolean isAdmin = false;
                var roles = userDetails.getAuthorities();
                for (var role : roles) {
                    if (role.getAuthority().equals("ROLE_ADMIN")) {
                        isAdmin = true;
                        break;
                    }
                }

                String newToken = String.format("%s %s", tokenPrefix, jwtTokenUtil.generateToken(userDetails));
                return ResponseEntity.ok().body(new AuthResponse(isAdmin, true, newToken, username));
            } else {
                return ResponseEntity.badRequest().body(new AuthResponse(false, false, "Not Valid token", ""));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(false));
        }
    }

    /**
     * @param authenticationRequest {@code JwtRequest JwtRequest} object
     * @return {@code AuthResponse AuthResponse} object
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = String.format("%s %s", tokenPrefix, jwtTokenUtil.generateToken(userDetails));

        Optional<User> optionalUser = userRepository.findByUsernameAndActiveTrue(authenticationRequest.getUsername());
        if (optionalUser.isPresent()) {
            // Checking whether admin or not
            boolean isAdmin = false;
            Set<Role> roles = optionalUser.get().getRoles();
            for (Role role : roles) {
                if (role.getName().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }

            return ResponseEntity.ok().body(new AuthResponse(isAdmin, true, token, optionalUser.get().getUsername()));
        } else {
            return ResponseEntity.badRequest().body(new AuthResponse(false));
        }
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
