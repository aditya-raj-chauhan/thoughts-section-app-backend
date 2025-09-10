package com.devotion.thoughts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "quotes-book")  // ✅ Correct
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Data



public class QuotesModel {
    @Id
    private String id;

    private String username;
    private String quote;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
