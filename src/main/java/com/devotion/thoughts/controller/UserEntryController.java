package com.devotion.thoughts.controller;

import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserEntryController {

    @Autowired
    private UserService userServiceObj;

    // ✅ Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userServiceObj.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();  // 204
        }
        return ResponseEntity.ok(users); // 200
    }

    // ✅ Get user by username
    @GetMapping("/id/{username}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String username) {
        UserModel user = userServiceObj.getUserByUserName(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if not found
        } else {
            return ResponseEntity.ok(user); // 200
        }
    }

    // ✅ Add new user
    @PostMapping("/adduser")
    public ResponseEntity<UserModel> addANewUser(@RequestBody UserModel newUser) {
        try {
            UserModel savedUser = userServiceObj.addAUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Update user
    @PutMapping("/id/{username}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String username,
                                                @RequestBody UserModel newUserData) {
        UserModel existingUser = userServiceObj.getUserByUserName(username);

        if (existingUser != null) {
            // If username is changing AND already exists in DB, return 409 Conflict
            UserModel conflictCheck = userServiceObj.getUserByUserName(newUserData.getUsername());
            if (conflictCheck != null && !newUserData.getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
            }

            // Delete old user
            userServiceObj.deleteUserByUsername(username);

            // Save new user (with new username + password)
            UserModel updatedUser = userServiceObj.addAUser(newUserData);

            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
    }

    // ✅ Delete user
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        UserModel existingUser = userServiceObj.getUserByUserName(username);

        if (existingUser != null) {
            userServiceObj.deleteUserByUsername(username);
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
        }
    }
}
