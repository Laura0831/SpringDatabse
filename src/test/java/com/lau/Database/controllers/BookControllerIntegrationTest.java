package com.lau.Database.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.Services.BookService;
import com.lau.Database.TestDataUtil;
import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;
import com.lau.Database.domain.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; //uses field Injection, so a constructor is not needed
    private final ObjectMapper objMap = new ObjectMapper();

    @Autowired
    private BookService bookService;

    @Test
    public void CreateBookSuccessfully() throws Exception {
        BookDto book = TestDataUtil.createTestBookDto(null);
        String BookJson = objMap.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(BookJson) //We need to post a JSON object to the endpoint
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }


    @Test
    public void CreateBookSuccessfullyAndReturnBook() throws Exception {
        BookDto book = TestDataUtil.createTestBookDto(null);
        String BookJson = objMap.writeValueAsString(book);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(BookJson) //We need to post a JSON object to the endpoint
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Acotar"));

    }



    @Test
    public void ListBookReturned() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );

    }


    @Test
    public void ActualListBookReturned() throws Exception {

        BookEntity book = TestDataUtil.createTestBook3(null); //creates one author on the database
        bookService.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-9848-0675-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("People we meet on Vacation")); //similar to AssertTrue on Junit

    }


    //READ Method Test
    @Test
    public void BookFound() throws Exception {

        BookEntity book = TestDataUtil.createTestBook2(null); //creates one author on the database
        bookService.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/"+ book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );

    }

    @Test
    public void BookNotFound() throws Exception {

        BookEntity book = TestDataUtil.createTestBook2(null); //creates one author on the database

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/"+ book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()
                );

    }


    @Test
    public void BookFoundAndCorrect() throws Exception {

        BookEntity book = TestDataUtil.createTestBook2(null); //creates one author on the database
        bookService.createBook(book.getIsbn(), book);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/"+ book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-0-5934-4087-2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book Lovers")
                );

    }


    //makes sure the book is updated and the status is a 200 (update)
    @Test
    public void BookUpdatedSuccessfully() throws Exception {
        BookEntity book = TestDataUtil.createTestBook(null); //creates a book object in the database
        BookEntity savedBook = bookService.createBook(book.getIsbn(), book);

        BookDto tempBook = TestDataUtil.createTestBookDto(null);
        tempBook.setIsbn(savedBook.getIsbn());
        String bookJson = objMap.writeValueAsString(tempBook);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/978-1-2345-6789-0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );


    }

    // makes sure the updated book is done correctly with the values correct
    @Test
    public void BookUpdatedCorrect() throws Exception {
        BookEntity book = TestDataUtil.createTestBook2(null); //creates a book object in the database
        BookEntity savedBook = bookService.createBook(book.getIsbn(), book);

        BookDto tempBook = TestDataUtil.createTestBookDto(null);
        tempBook.setIsbn(savedBook.getIsbn());
        tempBook.setTitle("Funny Story");
        String bookJson = objMap.writeValueAsString(tempBook);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("978-0-5934-4087-2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Funny Story")
                );


    }



    @Test
    public void BookPartialUpdateSuccessfully() throws Exception {
        BookEntity book = TestDataUtil.createTestBook(null); //creates a book object in the database
        BookEntity savedBook = bookService.createBook(book.getIsbn(), book);

        BookDto tempBook = TestDataUtil.createTestBookDto(null);
        tempBook.setTitle("Acowar");
        String bookJson = objMap.writeValueAsString(tempBook);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/books/" + book.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );

    }


    // makes sure the updated book is done correctly with the values correct
    @Test
    public void BookPartialUpdateCorrect() throws Exception {
        BookEntity book = TestDataUtil.createTestBook2(null); //creates a book object in the database
        BookEntity savedBook = bookService.createBook(book.getIsbn(), book);

        BookDto tempBook = TestDataUtil.createTestBookDto(null);
        tempBook.setTitle("Funny Story");
        String bookJson = objMap.writeValueAsString(tempBook);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Funny Story")
                );


    }




    @Test
    public void BookDeletedCorrect(){

    }



}
