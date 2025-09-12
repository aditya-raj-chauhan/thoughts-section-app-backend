package com.devotion.thoughts.controller;

import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<UserModel> user = userServiceObj.getUserByUserName(username);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<UserModel> addANewUser(@RequestBody UserModel newUser) {
        try {
            UserModel savedUser = userServiceObj.addAUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            // log it
            System.out.println(e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // ✅ Update an existing user (can change both username and password safely)
    @PutMapping("/id/{username}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String username,
                                                @RequestBody UserModel newUserData) {
        Optional<UserModel> existingOpt = userServiceObj.getUserByUserName(username);

        if (existingOpt.isPresent()) {
            // If username is changing AND already exists in DB, return 409 Conflict
            Optional<UserModel> conflictCheck = userServiceObj.getUserByUserName(newUserData.getUsername());
            if (conflictCheck.isPresent() && !newUserData.getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
            }

            // Delete old user
            userServiceObj.deleteUserByUsername(username);

            // Save new user (with new username + password)
            UserModel updatedUser = userServiceObj.addAUser(newUserData);

            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if old user not found
        }
    }


    // ✅ Delete user by username
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        Optional<UserModel> existingOpt = userServiceObj.getUserByUserName(username);

        if (existingOpt.isPresent()) {
            userServiceObj.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
