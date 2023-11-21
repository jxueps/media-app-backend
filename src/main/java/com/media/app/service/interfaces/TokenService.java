package com.media.app.service.interfaces;

import com.media.app.entity.Token;
import com.media.app.entity.User;

import java.util.List;
import java.util.Optional;

public interface TokenService {

    Token save(Token token);

    List<Token> findAllValidTokensByUser(Long userId);

    Optional<Token> findByTokenString(String tokenString);

    List<Token> saveAll(List<Token> tokens);
}
