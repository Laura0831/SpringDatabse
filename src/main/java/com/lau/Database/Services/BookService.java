package com.lau.Database.Services;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface BookService {
    public BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    //overloading
    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void delete(String isbn);
}
