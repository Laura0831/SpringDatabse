package com.lau.Database.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lau.Database.TestDataUtil;
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


}
