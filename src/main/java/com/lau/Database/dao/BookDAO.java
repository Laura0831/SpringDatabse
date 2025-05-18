package com.lau.Database.dao;

import com.lau.Database.domain.Book;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookDAO{
    void create(Book book);

    Optional<Book> find(String isbn);

    List<Book> findMany();

    void update(String isbn, Book book);

    void delete(String isbn);
}
