package com.lau.Database.dao;


import com.lau.Database.TestDataUtil;
import com.lau.Database.dao.impl.AuthorDaoImpl;
import com.lau.Database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //this allows for spring boot to remove and
// clean the database author creation after every test case is run. This prevents multiple creation of the same author
public class authorDaoImplTest {


    @Mock
    private JdbcTemplate JdbcTemp;


    @InjectMocks
    private AuthorDaoImpl underTest;


    @Test
    public void correctSQL(){

        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);

        verify(JdbcTemp).update(
                eq("INSERT INTO authors(id, name, age) VALUES (?,?,?)"),
                eq(1L),
                eq("Abigail Rose"),
                eq(80)

        );


    }

    @Test
    public void FindOneAuthor(){
        underTest.findOne(1L);

        verify(JdbcTemp).query(
                eq("SELECT id, name, age FROM authors WHERE id= ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }


    @Test
    public void FindManySQL(){
        underTest.find();
        verify(JdbcTemp).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }


    @Test
    public void UpdateFullSQL(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.update(5L,author);

        verify(JdbcTemp).update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                1L,
                "Abigail Rose",
                80,
                5L
        );
    }


    @Test
    public void DeleteSQL(){
        underTest.delete(1L);
        verify(JdbcTemp).update("DELETE FROM authors WHERE id = ?", 1L);
    }


}
