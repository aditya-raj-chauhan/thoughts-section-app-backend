package com.devotion.thoughts.controller;

import com.devotion.thoughts.model.QuotesModel;
import com.devotion.thoughts.service.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quotes")
public class QuotesEntryController {

    @Autowired
    private QuotesService serviceObject;

    // ✅ Get all quotes
    @GetMapping("/all")
    public ResponseEntity<List<QuotesModel>> getAll() {
        List<QuotesModel> quotes = serviceObject.getAllQuotes();
        if (quotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    // ✅ Add a new quote
    @PostMapping("/add")
    public ResponseEntity<QuotesModel> addQuote(@RequestBody QuotesModel entryData) {
        try {
            QuotesModel saved = serviceObject.addAQuote(entryData);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Get quote by id
    @GetMapping("/{id}")
    public ResponseEntity<QuotesModel> getQuoteById(@PathVariable String id) {
        Optional<QuotesModel> responseData = serviceObject.getById(id);
        return responseData
                .map(data -> new ResponseEntity<>(data, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ✅ Edit an existing quote by id
    @PutMapping("/{id}")
    public ResponseEntity<QuotesModel> editQuoteById(@PathVariable String id, @RequestBody QuotesModel newEntry) {
        Optional<QuotesModel> oldEntryOpt = serviceObject.getById(id);

        if (oldEntryOpt.isPresent()) {
            QuotesModel oldEntry = oldEntryOpt.get();
            oldEntry.setUsername(newEntry.getUsername());
            oldEntry.setQuote(newEntry.getQuote());

            QuotesModel updated = serviceObject.addAQuote(oldEntry);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Delete quote by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteById(@PathVariable String id) {
        Optional<QuotesModel> responseData = serviceObject.getById(id);
        if (responseData.isPresent()) {
            serviceObject.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
