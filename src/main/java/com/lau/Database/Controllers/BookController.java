package com.lau.Database.Controllers;


import com.lau.Database.Services.BookService;
import com.lau.Database.domain.Entity.BookEntity;
import com.lau.Database.domain.dto.BookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.lau.Database.Mappers.Mapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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



    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook_UpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto Book){

        BookEntity bookEntity = BookMapper.mapFrom(Book); //Converts from plain object to a database object
        boolean foundBook = bookService.isExists(isbn); //we want to check if it exists BEFORE we create a new book
        BookEntity savedBook = bookService.createBook(isbn, bookEntity); //calls the service class for book and that calls the create method to add the new databse object
        BookDto bookDto = BookMapper.mapTo(savedBook);

        if(foundBook){
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
        }

    }




    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialBookUpdate(@PathVariable("isbn") String isbn, @RequestBody BookDto Book){

        BookEntity bookEntity = BookMapper.mapFrom(Book); //Converts from plain object to a database object
        boolean foundBook = bookService.isExists(isbn); //we want to check if it exists BEFORE we create a new book
        if(!foundBook){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            BookEntity savedBook = bookService.partialUpdate(isbn, bookEntity); //calls the service class for book and that calls the create method to add the new databse object
            BookDto bookDto = BookMapper.mapTo(savedBook);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }
    }







    @GetMapping(path = "/books")
    public List<BookDto> listOfBooks(){
        List<BookEntity> books = bookService.findAll();
        return books.stream().map(BookMapper::mapTo).collect(Collectors.toList());
    }


    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> GetBook(@PathVariable("isbn") String isbn){

        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(BookEntity-> { //If foundBook contains a value (i.e., the Book was found), it executes the lambda function:
            BookDto book = BookMapper.mapTo(BookEntity);
            return new ResponseEntity<>(book, HttpStatus.OK); //creates a 200 OK HTTP response with the BookDto as the body
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }




}
