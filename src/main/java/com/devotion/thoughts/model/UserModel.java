package com.devotion.thoughts.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users-Db")
@Data
public class UserModel {

    @Id   // ✅ Mongo will now treat username as the primary key
    private String username;

    private String password;

    @DBRef
    private List<QuotesModel> userQuotes = new ArrayList<>();
}
