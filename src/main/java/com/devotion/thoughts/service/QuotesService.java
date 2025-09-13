package com.devotion.thoughts.service;

import com.devotion.thoughts.model.QuotesModel;
import com.devotion.thoughts.model.UserModel;
import com.devotion.thoughts.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuotesService {

    @Autowired
    private QuotesRepository quotesRepositoryObject;
    @Autowired
    UserService serviceObjForQuotes;

    // ✅ Fetch all quotes
    public List<QuotesModel> getAllQuotes() {
        return quotesRepositoryObject.findAll();
    }

    // ✅ Add or update a quote (returns saved entity)
    public QuotesModel addAQuote(QuotesModel quotesData, String username) {
        quotesData.setDate(LocalDateTime.now());
        quotesData.setUsername(username);
        UserModel user = serviceObjForQuotes.getUserByUserName(username);
        QuotesModel savedData= quotesRepositoryObject.save(quotesData);
         user.getUserQuotes().add(savedData);
         serviceObjForQuotes.addAUser(user);
         return savedData;
    }


    // ✅ Add or update a quote (returns saved entity)
    public QuotesModel addAQuote(QuotesModel quotesData) {
        quotesData.setDate(LocalDateTime.now());


        return quotesRepositoryObject.save(quotesData);


    }

    // ✅ Get by ID
    public QuotesModel getById(String id) {
        return quotesRepositoryObject.findById(id).orElse(null);
    }

    // ✅ Delete by ID
    public void deleteById(String id, String username) {
        UserModel user = serviceObjForQuotes.getUserByUserName(username);
        user.getUserQuotes().removeIf(x->x.getId().equals(id));
        serviceObjForQuotes.addAUser(user);
        quotesRepositoryObject.deleteById(id);
    }
}
