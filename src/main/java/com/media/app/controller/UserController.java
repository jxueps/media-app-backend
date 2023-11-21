package com.media.app.controller;

import com.media.app.dto.ErrorResponse;
import com.media.app.dto.JwtAuthenticationResponse;
import com.media.app.dto.LoginRequest;
import com.media.app.dto.RegisterRequest;
import com.media.app.dto.TokenResponse;
import com.media.app.service.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse(String.join(", ", errors)));
        }

        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponse(jwtAuthenticationResponse.getToken(), "BEARER"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse(String.join(", ", errors)));
        }

        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.login(loginRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new TokenResponse(jwtAuthenticationResponse.getToken(), "BEARER"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
