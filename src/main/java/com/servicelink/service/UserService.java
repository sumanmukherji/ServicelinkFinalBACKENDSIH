package com.servicelink.service;

import org.springframework.stereotype.Service;

import com.servicelink.entity.User;
import com.servicelink.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

public User loginUser(String email, String password) {

    User user = userRepository.findByEmail(email).orElse(null);

    if (user != null && user.getPassword().equals(password)) {
        return user;
    }

    return null;
}
}