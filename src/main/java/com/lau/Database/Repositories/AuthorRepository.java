package com.lau.Database.Repositories;

import com.lau.Database.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

   //no implementation is needed as Spring JPA is clever to workout an implementation based
    //on how you name the methods in the interface. This one connected the word Age in the
    //method name and knew how to implement it.
    Iterable<Author> AgeLessThan(int age);

    @Query("SELECT a from Author a where a.age > ?1")
    Iterable<Author> findAuthorsWithAgeGreaterThan(int age);
}
