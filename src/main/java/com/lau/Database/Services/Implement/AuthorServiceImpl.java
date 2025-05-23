package com.lau.Database.Services.Implement;


import com.lau.Database.Repositories.AuthorRepository;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.domain.Entity.AuthorEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

   private AuthorRepository authorRepo;

   public AuthorServiceImpl(AuthorRepository authorRepo){
       this.authorRepo = authorRepo;
   }


    // Takes in a database object and inserts it into the database and returns the new Object but with a ID set
    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {

        return authorRepo.save(authorEntity); //implements the JPA save function

    }
}
