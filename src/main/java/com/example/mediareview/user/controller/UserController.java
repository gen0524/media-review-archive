package com.example.mediareview.user.controller;

import com.example.mediareview.user.dto.UserRequest;
import com.example.mediareview.user.dto.UserResponse;
import com.example.mediareview.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody UserRequest request) {
        return userService.login(request);
    }
}