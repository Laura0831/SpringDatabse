package com.lau.Database.Services.Implement;

import com.lau.Database.Repositories.BookRepository;
import com.lau.Database.Services.BookService;
import com.lau.Database.domain.Entity.BookEntity;
import org.springframework.stereotype.Service;

@Service //is a bean class
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
