package com.servicelink.controller;

import org.springframework.web.bind.annotation.*;

import com.servicelink.entity.User;
import com.servicelink.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

@PostMapping("/login")
public User login(@RequestBody User user) {
    return userService.loginUser(user.getEmail(), user.getPassword());
}
}