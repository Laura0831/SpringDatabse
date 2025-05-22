package com.lau.Database.Controllers;


import com.lau.Database.Services.AuthorService;
import com.lau.Database.domain.dto.AuthorDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;

    //Constructor
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }


    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author){//parameter comes in as java object, converted from json

        return authorService.createAuthor(author);
    }




}
