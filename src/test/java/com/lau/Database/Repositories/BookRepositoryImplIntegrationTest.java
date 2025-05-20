package com.lau.Database.Repositories;


import com.lau.Database.TestDataUtil;
import com.lau.Database.domain.Author;
import com.lau.Database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryImplIntegrationTest {

    private BookRepository underTest;


    // The autowired annotation tells spring to inject dependencies as declared in this constructor
    @Autowired
    public BookRepositoryImplIntegrationTest(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testBookCreation(){

        Author author = TestDataUtil.createTestAuthor(); //we got to create an author to be allowed access to the foreign key that is connected to the author

        Book book = TestDataUtil.createTestBook(author);
        //reseting the book value to the new created book that is connected to author, allows for
        // comparison of the database bookcreated with author and book from the database( which has the author id attached)
        //This allows for the test to pass
        book = underTest.save(book);
        Optional<Book> result =  underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void MultiplBooksCreated(){
        //creates one author that has multiple books
        Author author = TestDataUtil.createTestAuthor2();

        //book 2
        Book book2 = TestDataUtil.createTestBook2(author);
        book2 = underTest.save(book2);

        //book 3
        Book book3 = TestDataUtil.createTestBook3(author);
        book3 = underTest.save(book3);

       Iterable<Book> result = underTest.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(book2, book3);

    }

    @Test
    public void updateBooks(){
        Author author = TestDataUtil.createTestAuthor3();

        Book book4 = TestDataUtil.createTestBook4(author);
        underTest.save(book4);

        book4.setTitle("Throne of Glass");
        book4 = underTest.save(book4);

        Optional<Book> result = underTest.findById(book4.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book4);

    }

    @Test
    public void BookDeleted(){
        Author author = TestDataUtil.createTestAuthor();

        Book book = TestDataUtil.createTestBook(author);
        book = underTest.save(book);

        underTest.deleteById(book.getIsbn());
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }


}
