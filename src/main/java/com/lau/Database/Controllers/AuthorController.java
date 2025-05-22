package com.lau.Database.Controllers;


import com.lau.Database.Mappers.Mapper;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.dto.AuthorDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    //Constructor
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }


    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author){//parameter comes in as java object, converted from json
        AuthorEntity authorEntity = authorMapper.mapFrom(author); //Converts from plain object to a database object
        AuthorEntity savedAuthor = authorService.createAuthor(authorEntity); //saves the new databse input and gets back the new created databse object

        //after inserting the new author in the database, the object is mapped back into a plain object but with the new id
        return authorMapper.mapTo(savedAuthor);

    }




}
