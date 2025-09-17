package com.devotion.thoughts.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class UserModel {
    @Id
    private String username;
    private String password;
    private String role; // USER or ADMIN
    private List<QuotesModel> userQuotes;
}
