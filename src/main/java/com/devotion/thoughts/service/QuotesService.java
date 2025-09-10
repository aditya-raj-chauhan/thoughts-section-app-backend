package com.devotion.thoughts.service;

import com.devotion.thoughts.model.QuotesModel;
import com.devotion.thoughts.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuotesService {

    @Autowired
    private QuotesRepository quotesRepositoryObject;

    // ✅ Fetch all quotes
    public List<QuotesModel> getAllQuotes() {
        return quotesRepositoryObject.findAll();
    }

    // ✅ Add or update a quote (returns saved entity)
    public QuotesModel addAQuote(QuotesModel quotesData) {
        quotesData.setDate(LocalDateTime.now());
        return quotesRepositoryObject.save(quotesData);
    }

    // ✅ Get by ID
    public Optional<QuotesModel> getById(String id) {
        return quotesRepositoryObject.findById(id);
    }

    // ✅ Delete by ID
    public void deleteById(String id) {
        quotesRepositoryObject.deleteById(id);
    }
}
