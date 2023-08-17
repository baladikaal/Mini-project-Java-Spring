package com.baladika.baladikaAPI.controller;

import com.baladika.baladikaAPI.entity.UserEntity;
import com.baladika.baladikaAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user) {
        String token = authService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody UserEntity user) {
        UserEntity newUser = authService.register(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(newUser);
    }

}

