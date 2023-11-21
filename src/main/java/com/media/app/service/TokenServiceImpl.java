package com.media.app.service;

import com.media.app.dao.TokenRepository;
import com.media.app.entity.Token;
import com.media.app.service.interfaces.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public List<Token> findAllValidTokensByUser(Long userId) {
        return tokenRepository.findAllValidTokensByUser(userId);
    }

    @Override
    public Optional<Token> findByTokenString(String tokenString) {
        return tokenRepository.findByTokenString(tokenString);
    }

    @Override
    public List<Token> saveAll(List<Token> tokens) {
        return tokenRepository.saveAll(tokens);
    }
}
