package com.media.app.service;
import com.media.app.dto.JwtAuthenticationResponse;
import com.media.app.dto.LoginRequest;
import com.media.app.dto.RegisterRequest;
import com.media.app.entity.Role;
import com.media.app.entity.User;
import com.media.app.error.UserRegistrationException;
import com.media.app.service.interfaces.JwtService;
import com.media.app.service.interfaces.TokenService;
import com.media.app.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void testRegister() throws UserRegistrationException {
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john@example.com", "password");

        when(userService.existsByEmail(registerRequest.getEmail().trim())).thenReturn(false);
        when(userService.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedJwtToken");

        JwtAuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("mockedJwtToken", response.getToken());

        verify(userService, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    public void testLogin() {
        LoginRequest loginRequest = new LoginRequest("john@example.com", "password");
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        when(userService.findByEmail(loginRequest.getEmail())).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedJwtToken");

        JwtAuthenticationResponse response = authenticationService.login(loginRequest);

        assertNotNull(response);
        assertEquals("mockedJwtToken", response.getToken());

        verify(jwtService, times(1)).generateToken(any(User.class));
    }

    @Test
    public void testLoginInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest("john@example.com", "invalidPassword");

        when(userService.findByEmail(loginRequest.getEmail())).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> authenticationService.login(loginRequest));

        verify(userService, times(1)).findByEmail(loginRequest.getEmail());
    }
}
