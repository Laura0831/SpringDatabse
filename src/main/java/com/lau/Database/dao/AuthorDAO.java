package com.lau.Database.dao;

import com.lau.Database.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {


    void create(Author author);

    //if we find an author we will return it, otherwise we will return an empty optional
    Optional<Author> findOne(long l);

    List<Author> find();

    void update(Long id, Author author);

    void delete(long id);
}
