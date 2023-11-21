package com.media.app.service.interfaces;

import com.media.app.dto.JwtAuthenticationResponse;
import com.media.app.dto.LoginRequest;
import com.media.app.dto.RegisterRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse register(RegisterRequest request);

    JwtAuthenticationResponse login(LoginRequest request);
}