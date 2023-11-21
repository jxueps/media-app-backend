package com.media.app.service;

import com.media.app.dao.UserRepository;
import com.media.app.entity.User;
import com.media.app.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public Optional<User> findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById (Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) { return userRepository.save(user); }

    public Boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }
}
