package com.devotion.thoughts.repository;

import com.devotion.thoughts.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel,String> {
    public UserModel findByUsername(String username);
}
