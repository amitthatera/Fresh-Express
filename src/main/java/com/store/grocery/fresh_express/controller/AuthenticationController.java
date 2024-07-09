package com.store.grocery.fresh_express.controller;

import com.store.grocery.fresh_express.dto.AuthenticationRequest;
import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.security.JwtService;
import com.store.grocery.fresh_express.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationController(UserDetailsService userDetailsService, UserService userService,
                                    AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequest request){
        try {
            authenticateDetails(request.username(), request.password());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
            String token = jwtService.generateAccessToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Username or Password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO){
        UserDTO user = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    private void authenticateDetails(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new
                UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authToken);
    }
}

