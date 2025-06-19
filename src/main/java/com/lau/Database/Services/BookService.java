package com.lau.Database.Services;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;

public interface BookService {
    public BookEntity createBook(String isbn, BookEntity book);
}
