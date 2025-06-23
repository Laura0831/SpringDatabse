package com.lau.Database.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.TestDataUtil;
import com.lau.Database.domain.Entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; //uses field Injection, so a constructor is not needed

    @Autowired
    private AuthorService authorService;
    private final ObjectMapper objMap = new ObjectMapper();



    //POST/CREATE Method Tests

    @Test //test to make sure author was created successfully
    public void CreateAuthorSuccessfully() throws Exception {
        AuthorEntity author1 = TestDataUtil.createTestAuthor(); //creates author
        author1.setId(null);
        String authorJSON = objMap.writeValueAsString(author1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON) //We need to post a JSON object to the endpoint
        )
                .andExpect(MockMvcResultMatchers.status().isCreated()); //similar to expectTrue on Junit
    }


    @Test //Test to make sure author was created successfully by return set author
    public void CreateAuthorSuccessfullyAndReturnAuthor() throws Exception {
        AuthorEntity author1 = TestDataUtil.createTestAuthor2(); //creates author
        author1.setId(null);
        String authorJSON = objMap.writeValueAsString(author1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJSON) //We need to post a JSON object to the endpoint
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Emily Henry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45)); //similar to AssertTrue on Junit
    }




    //GET Method Tests

    @Test
    public void ListAuthorReturned() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk()
        );
    }


    @Test
    public void ActualListAuthorReturned() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor(); //creates one author on the database
        authorService.createAuthor(author);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(80)); //similar to AssertTrue on Junit

    }

    //READ method tests

    @Test
    public void AuthorFound() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        authorService.createAuthor(author);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void AuthorNotFound() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        authorService.createAuthor(author);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/5")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    public void AuthorFoundAndCorrect() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        authorService.createAuthor(author);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Emily Henry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45)
                );
    }

    @Test
    public void AuthorDeletedCorrect(){

    }

}
