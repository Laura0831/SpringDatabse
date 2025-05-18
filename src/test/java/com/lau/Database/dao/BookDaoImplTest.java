package com.lau.Database.dao;

import com.lau.Database.TestDataUtil;
import com.lau.Database.dao.impl.AuthorDaoImpl;
import com.lau.Database.dao.impl.BookDaoImpl;
import com.lau.Database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //this allows for spring boot to remove and
// clean the database author creation after every test case is run. This prevents multiple creation of the same author
public class BookDaoImplTest {

    @Mock
    private JdbcTemplate JdbcTemp;


    @InjectMocks
    private BookDaoImpl underTest;


    @Test
    public void correctSQL() {
        Book book = TestDataUtil.createTestBook();
        underTest.create(book);

        verify(JdbcTemp).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)"),
                eq("978-1-2345-6789-0"),
                eq("The shadow in the Attic"),
                eq(1L)
        );
    }


    @Test
    public void FindOneBook(){
        underTest.find("978-1-2345-6789-0");

        verify(JdbcTemp).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn= ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("978-1-2345-6789-0")
        );
    }


    @Test
    public void FindManyBooks(){
        underTest.findMany();
        verify(JdbcTemp).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }



    @Test
    public void updateBooks(){
        Book book = TestDataUtil.createTestBook();
        underTest.update("978-1-2345-6789-0", book);
        verify(JdbcTemp).update("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                "978-1-2345-6789-0",
                "The shadow in the Attic",
                1L,
                "978-1-2345-6789-0");
    }


    @Test
    public void DeleteBook(){
        underTest.delete("978-1-2345-6789-0");
        verify(JdbcTemp).update("DELETE FROM books WHERE isbn = ?", "978-1-2345-6789-0");
    }



}
