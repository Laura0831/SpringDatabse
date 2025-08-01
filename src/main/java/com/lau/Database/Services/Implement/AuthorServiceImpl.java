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
    public AuthorEntity save(AuthorEntity authorEntity) {

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

    @Override
    public boolean isExists(Long id) {
        return authorRepo.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity newAuthor) {

       newAuthor.setId(id);
       return authorRepo.findById(id).map(existingAuthor -> {
           Optional.ofNullable(newAuthor.getName()).ifPresent(existingAuthor::setName);

           // if the author provided has an age and is not null then we want to set that on the existing author that we found
           //from the database
           Optional.ofNullable(newAuthor.getAge()).ifPresent(existingAuthor::setAge);
          return authorRepo.save(existingAuthor); //returns the updated and saved author back to Map()
       }).orElseThrow(()-> new RuntimeException("Author does not exist!"));



    }

    @Override
    public void delete(Long id) {
        authorRepo.deleteById(id);

    }
}
