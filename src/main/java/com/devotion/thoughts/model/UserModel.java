package com.devotion.thoughts.model;

import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users-Db")
@Data
public class UserModel {
    @Indexed(unique = true)
    @NonNull
    public String username;
    @NonNull
    public String password;
    @DBRef
    private List<QuotesModel> userQuotes = new ArrayList<>();
}
