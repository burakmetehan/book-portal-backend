package tr.com.obss.jip.springfinal.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springfinal.config.JwtTokenUtil;
import tr.com.obss.jip.springfinal.model.JwtRequest;
import tr.com.obss.jip.springfinal.model.JwtResponse;
import tr.com.obss.jip.springfinal.service.JwtUserDetailsService;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;


    public JwtAuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        // HttpHeaders responseHeaders = new HttpHeaders();
        // responseHeaders.add(HttpHeaders.AUTHORIZATION, token);
        // responseHeaders.add("Expiration", jwtTokenUtil.getExpirationDateFromToken(token).toString());

        // return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
        // return ResponseEntity.ok().header("Authorization", token).body(new JwtResponse(token));
        // return ResponseEntity.ok().headers(responseHeaders).body(new JwtResponse(token));
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }
}
