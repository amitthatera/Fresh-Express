package com.store.grocery.fresh_express.controller;

import com.store.grocery.fresh_express.dto.AuthenticationRequest;
import com.store.grocery.fresh_express.dto.RequestResponse;
import com.store.grocery.fresh_express.dto.UserDTO;
import com.store.grocery.fresh_express.security.JwtService;
import com.store.grocery.fresh_express.service.UserService;
import com.store.grocery.fresh_express.service.impl.ActivationCodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final ActivationCodeService activationCodeService;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationController(UserDetailsService userDetailsService, UserService userService,
                                    ActivationCodeService activationCodeService, AuthenticationManager authenticationManager,
                                    JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.activationCodeService = activationCodeService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<RequestResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        try {
            authenticateDetails(request.username(), request.password());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
            String token = jwtService.generateAccessToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(RequestResponse.builder()
                            .timestamp(Instant.now().toString())
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .token(token)
                            .refreshToken(refreshToken)
                            .message("Login Successfully!!")
                            .build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(RequestResponse.builder()
                            .timestamp(Instant.now().toString())
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .status(HttpStatus.UNAUTHORIZED)
                            .message("Incorrect Username or Password!!")
                            .build());
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RequestResponse.builder()
                            .timestamp(Instant.now().toString())
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .status(HttpStatus.FORBIDDEN)
                            .message("Activate your account before login!!")
                            .build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RequestResponse> register(@Valid @RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED)
                        .message("User Registered Successfully!!")
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@Valid @RequestBody RequestResponse refreshRequest){
        String token = refreshRequest.refreshToken();
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(token));
        if(userDetails != null && jwtService.isTokenValid(token, userDetails)){
            String newAccessToken = jwtService.generateAccessToken(userDetails);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(RequestResponse.builder()
                            .timestamp(Instant.now().toString())
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK)
                            .token(newAccessToken)
                            .refreshToken(refreshRequest.refreshToken())
                            .message("Token refreshed successfully")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Request Timeout!! Login Again!!")
                        .build());
    }

    @GetMapping("/activate-account")
    public ResponseEntity<RequestResponse> activateAccount(@RequestParam String token){
        activationCodeService.activateAccount(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RequestResponse.builder()
                        .timestamp(Instant.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Account Activated")
                        .build());
    }


    private void authenticateDetails(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new
                UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authToken);
    }
}

