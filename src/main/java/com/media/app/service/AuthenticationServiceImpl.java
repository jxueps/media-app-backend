package com.media.app.service;

import com.media.app.dto.JwtAuthenticationResponse;
import com.media.app.dto.LoginRequest;
import com.media.app.dto.RegisterRequest;
import com.media.app.entity.Role;
import com.media.app.entity.Token;
import com.media.app.entity.TokenType;
import com.media.app.entity.User;
import com.media.app.error.UserRegistrationException;
import com.media.app.service.interfaces.AuthenticationService;
import com.media.app.service.interfaces.JwtService;
import com.media.app.service.interfaces.TokenService;
import com.media.app.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // used to generate a constructor for initalising fields or fields marked with @NonNull or final
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse register (RegisterRequest request) throws UserRegistrationException {
        // check if email is already registered in database
        if (userService.existsByEmail(request.getEmail().trim())) {
            throw new UserRegistrationException("Email is already registered.");
        }

        // create and save user
        var user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .role(Role.USER)
                .build();

        user = userService.save(user);

        // create and save token
        var jwt = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwt);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse login (LoginRequest request) throws IllegalArgumentException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwt);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    private Token saveUserToken(User user, String jwt) {
        var token = Token
                .builder()
                .user(user)
                .tokenString(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        return tokenService.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validTokens = tokenService.findAllValidTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenService.saveAll(validTokens);
    }
}
