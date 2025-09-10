package com.devotion.thoughts.service;

import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // <-- missing in your code
public class UserService {

    @Autowired
    private UserRepository userRepositoryObj;

    // ✅ Fetch all users
    public List<UserModel> getAllUsers() {
        return userRepositoryObj.findAll();
    }

    // ✅ Add or update user
    public UserModel addAUser(UserModel userData) {
        return userRepositoryObj.save(userData);
    }

    // ✅ Get by ID
    public Optional<UserModel> getUserByUserName(String username) {
        return Optional.ofNullable(userRepositoryObj.findByUsername(username));
    }

    // ✅ Delete by ID
    public void deleteUserById(String id) {
        userRepositoryObj.deleteById(id);
    }
}
