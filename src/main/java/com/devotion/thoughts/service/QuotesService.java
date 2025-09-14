package com.devotion.thoughts.service;

import com.devotion.thoughts.model.QuotesModel;
import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuotesService {

    @Autowired
    private QuotesRepository quotesRepositoryObject;

    @Autowired
    private UserService serviceObjForQuotes;

    // ✅ Fetch all quotes
    public List<QuotesModel> getAllQuotes() {
        return quotesRepositoryObject.findAll();
    }

    // ✅ Add or update a quote (returns saved entity)
    @Transactional
    public QuotesModel addAQuote(QuotesModel quotesData, String username) {
        quotesData.setDate(LocalDateTime.now());
        quotesData.setUsername(username);

        UserModel user = serviceObjForQuotes.getUserByUserName(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        QuotesModel savedData = quotesRepositoryObject.save(quotesData);

        // Prevent NPE if userQuotes is null
        if (user.getUserQuotes() == null) {
            user.setUserQuotes(new ArrayList<>());
        }

        user.getUserQuotes().add(savedData);
        serviceObjForQuotes.addAUser(user);

        return savedData;
    }

    // ✅ Add or update a quote (returns saved entity)
    @Transactional
    public QuotesModel addAQuote(QuotesModel quotesData) {
        quotesData.setDate(LocalDateTime.now());
        return quotesRepositoryObject.save(quotesData);
    }

    // ✅ Get by ID
    public QuotesModel getById(String id) {
        return quotesRepositoryObject.findById(id).orElse(null);
    }

    // ✅ Delete by ID
    @Transactional
    public void deleteById(String id, String username) {
        UserModel user = serviceObjForQuotes.getUserByUserName(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        if (user.getUserQuotes() != null) {
            for (int i = 0; i < user.getUserQuotes().size(); i++) {
                QuotesModel q = user.getUserQuotes().get(i);
                if (q.getId().equals(id)) {
                    user.getUserQuotes().remove(i);
                    break;
                }
            }
        }

        serviceObjForQuotes.addAUser(user);
        quotesRepositoryObject.deleteById(id);
    }
}
