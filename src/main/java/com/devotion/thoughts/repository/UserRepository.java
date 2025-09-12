package com.devotion.thoughts.repository;

import com.devotion.thoughts.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

    // Find user by username (username is the @Id)
    UserModel findByUsername(String username);

    // Delete user by username
    void deleteByUsername(String username);
}
