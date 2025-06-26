package com.lau.Database.Controllers;


import com.lau.Database.Mappers.Mapper;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorService authorService; //calls the author service interface
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    //Constructor
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }


    //recivies a java object, creates a database of the java object (entity), push it to the database and gets the new object from database
    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){//parameter comes in as java object, converted from json
        AuthorEntity authorEntity = authorMapper.mapFrom(author); //Converts from plain object to a database object
        AuthorEntity savedAuthor = authorService.save(authorEntity); //saves the new databse input and gets back the new created databse object

        //after inserting the new author in the database, the object is mapped back into a plain object but with the new id
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);

    }


    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll(); //gets all the authors from the table

        //this will return everything in the database, so not the most optimize
        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList()); //converts the entities to Dto list

    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id); //returns an optional incase the author cannot be found in the databse

       return foundAuthor.map(authorEntity -> { //If foundAuthor contains a value (i.e., the author was found), it executes the lambda function:
            AuthorDto author = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(author, HttpStatus.OK); //creates a 200 OK HTTP response with the AuthorDto as the body
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); //If foundAuthor is empty, returns a response with 404 NOT FOUND status

    }


    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto author){

        if(!authorService.isExists(id)){
            return new ResponseEntity<AuthorDto>(HttpStatus.NOT_FOUND);
        }
        else{
            author.setId(id);
           AuthorEntity newAuthor =  authorMapper.mapFrom(author);
            AuthorEntity UpdatedAuthor = authorService.save(newAuthor);
            AuthorDto transformAuthor = authorMapper.mapTo(UpdatedAuthor);
            return new ResponseEntity<AuthorDto>(transformAuthor, HttpStatus.OK);
        }


    }


    @PatchMapping(path ="/authors/{id}" )
    public ResponseEntity<AuthorDto> partialUpdate(@PathVariable("id") Long id, @RequestBody AuthorDto author)){


    }




}
