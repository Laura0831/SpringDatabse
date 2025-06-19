package com.lau.Database.Controllers;


import com.lau.Database.domain.Entity.BookEntity;
import com.lau.Database.domain.dto.AuthorDto;
import com.lau.Database.domain.dto.BookDto;
import org.springframework.http.ResponseEntity;
import com.lau.Database.Mappers.Mapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> BookMapper;

    //constructor
    public BookController(Mapper<BookEntity, BookDto> bookMapper) {
        BookMapper = bookMapper;
    }



    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto Book){

        BookEntity bookEntity = BookMapper.mapFrom(Book); //Converts from plain object to a database object



        return new ResponseEntity<>();
    }


}
