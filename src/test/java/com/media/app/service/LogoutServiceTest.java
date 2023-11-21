package com.media.app.service;

import com.media.app.entity.Token;
import com.media.app.service.interfaces.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceTest {
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LogoutService logoutService;

    @Test
    public void testLogoutValidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        String validToken = "validJwtToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(tokenService.findByTokenString(validToken)).thenReturn(Optional.of(new Token()));

        logoutService.logout(request, response, authentication);

        verify(tokenService, times(1)).save(any(Token.class));
        verify(response, times(1)).setStatus(eq(HttpServletResponse.SC_OK));
    }

    @Test
    public void testLogoutInvalidToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        String invalidToken = "invalidJwtToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);
        when(tokenService.findByTokenString(invalidToken)).thenReturn(Optional.empty());

        logoutService.logout(request, response, authentication);

        verify(tokenService, never()).save(any(Token.class));
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testLogoutNoAuthorizationHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        verify(tokenService, never()).findByTokenString(anyString());
        verify(tokenService, never()).save(any(Token.class));
    }
}
