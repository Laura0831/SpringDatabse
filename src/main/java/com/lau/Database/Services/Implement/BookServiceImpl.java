package com.lau.Database.Services.Implement;

import com.lau.Database.Repositories.BookRepository;
import com.lau.Database.Services.BookService;
import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service //is a bean class
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    // Takes in a database object and inserts it into the database and returns the new database Object but with a ID set
    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn); //makes sure it saves the correct isbn for the book
        return bookRepository.save(book);//implements the JPA save function, to save the database object into the database
    }

    //returns a list of all the books in the database
    @Override
    public List<BookEntity> findAll() {
       return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }
}
