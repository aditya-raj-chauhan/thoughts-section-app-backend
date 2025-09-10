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
            return ResponseEntity.noContent().build();  // 204 if no users
        }
        return ResponseEntity.ok(users); // 200 with list
    }

    // ✅ Get user by ID
    @GetMapping("/id/{username}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String username) {
        Optional<UserModel> user = userServiceObj.getUserByUserName(username);
        return user.map(ResponseEntity::ok)   // 200 if found
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404 if not found
    }

    // ✅ Add a new user
    @PostMapping("/adduser")
    public ResponseEntity<UserModel> addANewUser(@RequestBody UserModel newUser) {
        UserModel savedUser = userServiceObj.addAUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // 201 Created
    }

    // ✅ Update an existing user
    @PutMapping("/id/{username}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String username, @RequestBody UserModel newUserData) {
        Optional<UserModel> oldDataOpt = userServiceObj.getUserByUserName(username);

        if (oldDataOpt.isPresent()) {
            UserModel oldData = oldDataOpt.get();
            oldData.setUsername(newUserData.getUsername());
            oldData.setPassword(newUserData.getPassword());

            UserModel updatedUser = userServiceObj.addAUser(oldData); // ✅ save back to DB
            return ResponseEntity.ok(updatedUser); // 200 OK with updated data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if user not found
        }
    }

    // ✅ Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        Optional<UserModel> existing = userServiceObj.getUserByUserName(id);
        if (existing.isPresent()) {
            userServiceObj.deleteUserById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
        }
    }
}
