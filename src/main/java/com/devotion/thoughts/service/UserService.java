package com.devotion.thoughts.service;

import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
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

    // ✅ Get by username
    public UserModel getUserByUserName(String username) {
        return userRepositoryObj.findByUsername(username);
    }

    // ✅ Delete by username
    public void deleteUserByUsername(String username) {
        userRepositoryObj.deleteByUsername(username);
    }
}
