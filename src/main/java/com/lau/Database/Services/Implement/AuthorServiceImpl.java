package com.lau.Database.Services.Implement;


import com.lau.Database.Repositories.AuthorRepository;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.domain.Entity.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

   private AuthorRepository authorRepo;

   public AuthorServiceImpl(AuthorRepository authorRepo){

       this.authorRepo = authorRepo;
   }


    // Takes in a database object and inserts it into the database and returns the new Object but with a ID set
    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {

        return authorRepo.save(authorEntity); //implements the JPA save function, to save the database object into the database

    }

    @Override
    public List<AuthorEntity> findAll() { //It retrieves all authors from the database and returns them as a List<Author>


       return StreamSupport.stream(authorRepo.findAll() //comes from the author repository
               .spliterator(), //Converts the Iterable returned by findAll() into a Spliterator
               false) //This converts the Spliterator into a sequential stream (false means not parallel)
               .collect(Collectors.toList()); //This collects all the elements in the stream into a List

    }

    @Override
    public Optional<AuthorEntity> findOne(long id) {

       return authorRepo.findById(id);



    }
}
