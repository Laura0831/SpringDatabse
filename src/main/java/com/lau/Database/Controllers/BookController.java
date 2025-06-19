package com.lau.Database.Controllers;


import com.lau.Database.Services.BookService;
import com.lau.Database.domain.Entity.BookEntity;
import com.lau.Database.domain.dto.AuthorDto;
import com.lau.Database.domain.dto.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.lau.Database.Mappers.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> BookMapper;
    private BookService bookService;

    //constructor
    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.BookMapper = bookMapper;
        this.bookService = bookService;
    }



    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto Book){

        BookEntity bookEntity = BookMapper.mapFrom(Book); //Converts from plain object to a database object
        BookEntity savedBook = bookService.createBook(isbn, bookEntity); //calls the service class for book and that calls the create method to add the new databse object
        BookDto bookDto = BookMapper.mapTo(savedBook);

        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }



    @GetMapping(path = "/books")
    public List<BookDto> listOfBooks(){
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(BookMapper::mapTo).collect(Collectors.toList());
    }


}
