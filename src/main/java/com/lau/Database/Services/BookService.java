package com.lau.Database.Services;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;

import java.util.List;

public interface BookService {
    public BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
