package com.devotion.thoughts.service;

import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor injection avoids circular dependencies
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Get all users
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Add or update user
    public UserModel addAUser(UserModel userData) {
        // encode password
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        return userRepository.save(userData);
    }

    // ✅ Get user by username
    public UserModel getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    // ✅ Delete by username
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
