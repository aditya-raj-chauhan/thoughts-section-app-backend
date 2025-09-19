    package com.devotion.thoughts.repository;

    import com.devotion.thoughts.model.QuotesModel;
    import org.springframework.data.mongodb.repository.MongoRepository;
    import org.springframework.stereotype.Repository;
//added a comment
    @Repository
    public interface QuotesRepository extends MongoRepository<QuotesModel,String> {

    }
