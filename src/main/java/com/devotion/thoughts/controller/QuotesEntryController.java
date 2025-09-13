package com.devotion.thoughts.controller;

import com.devotion.thoughts.model.QuotesModel;
import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.service.QuotesService;
import com.devotion.thoughts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuotesEntryController {

    @Autowired
    private QuotesService serviceObject;

    @Autowired
    private UserService serviceObjForQuotes;

    // ✅ Get all quotes of a user
    @GetMapping("/all/{username}")
    public ResponseEntity<List<QuotesModel>> getAllQuotesOfUser(@PathVariable String username) {
        UserModel user = serviceObjForQuotes.getUserByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // user not found
        }

        List<QuotesModel> quotes = user.getUserQuotes();
        if (quotes == null || quotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // user found but no quotes
        }

        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    // ✅ Add a new quote
    @PostMapping("/add/{username}")
    public ResponseEntity<QuotesModel> addQuote(@RequestBody QuotesModel entryData,
                                                @PathVariable String username) {
        try {
            QuotesModel saved = serviceObject.addAQuote(entryData, username);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Edit an existing quote by id (only if quote belongs to the user)
    @PutMapping("/{username}/{quoteId}")
    public ResponseEntity<QuotesModel> editQuoteById(@PathVariable String username,
                                                     @PathVariable String quoteId,
                                                     @RequestBody QuotesModel newEntry) {
        QuotesModel oldEntry = serviceObject.getById(quoteId);

        if (oldEntry != null && oldEntry.getUsername().equals(username)) {
            oldEntry.setQuote(newEntry.getQuote());   // update only the quote text
            QuotesModel updated = serviceObject.addAQuote(oldEntry); // save updated quote
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ✅ Delete quote by id (only if quote belongs to the user)
    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Void> deleteQuoteById(@PathVariable String id,
                                                @PathVariable String username) {
        QuotesModel existing = serviceObject.getById(id);
        if (existing != null && existing.getUsername().equals(username)) {
            serviceObject.deleteById(id, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
