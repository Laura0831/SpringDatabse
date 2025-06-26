package com.lau.Database.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lau.Database.Services.AuthorService;
import com.lau.Database.TestDataUtil;
import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.dto.AuthorDto;
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
        authorService.save(author);

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
        authorService.save(author);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }

    @Test
    public void AuthorNotFound() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        authorService.save(author);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/5")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    public void AuthorFoundAndCorrect() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        authorService.save(author);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/authors/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Emily Henry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(45)
                );
    }





    //UPDATE method Tests

    @Test
    public void AuthorUpdate_NoAuthorFound() throws Exception {

        AuthorDto author = TestDataUtil.createTestAuthor4DTO(); //creates one author on the database
        String authorJson = objMap.writeValueAsString(author); //converting a java object to a JSON string
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound()
                );
    }

    @Test
    public void AuthorUpdateFound() throws Exception {

        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        AuthorEntity savedAuthor = authorService.save(author);

        AuthorDto authorD = TestDataUtil.createTestAuthor2_DTO();
        String authorJson = objMap.writeValueAsString(authorD); //converting a java object to a JSON string
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isOk()
                );
    }


    @Test
    public void AuthorUpdateCorrectly() throws Exception {

        //this author is saved in the database
        AuthorEntity author = TestDataUtil.createTestAuthor2(); //creates one author on the database
        AuthorEntity savedAuthor = authorService.save(author); //it simulates that an author already exists in the database


        //this author is NOT saved in the database (temp author)
        AuthorEntity authorDTO = TestDataUtil.createTestAuthor3(); //creates a new author. The one that will be use to update the existing one
        authorDTO.setId(savedAuthor.getId()); //sets the new author id to the already existing one
        String authorJson = objMap.writeValueAsString(authorDTO); //creates a JSON to use as the update information that is send to the controller


        //send a PUT request to /authors/1 to update the existing author with the new name and age
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId()) //sends an HTTP put request to update the saved author
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(authorDTO.getAge())
                );
    }



    @Test
    public void PartialUpdate(){

    }





    @Test
    public void AuthorDeletedCorrect(){

    }

}
