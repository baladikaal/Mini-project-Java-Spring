package com.baladika.baladikaAPI.service;

import com.baladika.baladikaAPI.entity.UserEntity;
import com.baladika.baladikaAPI.jwt.JWT;
import com.baladika.baladikaAPI.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security's PasswordEncoder

    public String login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return generateToken(user.getId());
    }

    public UserEntity register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setActive(true); // Set isActive to true explicitly

        return userRepository.save(newUser);
    }

    private String generateToken(long userId) {
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + jwt.getAccessTokenExpired());

        String token = Jwts.builder()
                .setSubject(String.valueOf((userId)))
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiration)
                .signWith(SignatureAlgorithm.HS256, jwt.getSecretKey())
                .compact();

        return token;
    }
}

