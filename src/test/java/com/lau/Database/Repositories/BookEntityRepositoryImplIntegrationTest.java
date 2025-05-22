package com.lau.Database.Repositories;


import com.lau.Database.TestDataUtil;
import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryImplIntegrationTest {

    private BookRepository underTest;


    // The autowired annotation tells spring to inject dependencies as declared in this constructor
    @Autowired
    public BookEntityRepositoryImplIntegrationTest(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testBookCreation(){

        AuthorEntity authorEntity = TestDataUtil.createTestAuthor(); //we got to create an author to be allowed access to the foreign key that is connected to the author

        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        //reseting the book value to the new created book that is connected to author, allows for
        // comparison of the database bookcreated with author and book from the database( which has the author id attached)
        //This allows for the test to pass
        bookEntity = underTest.save(bookEntity);
        Optional<BookEntity> result =  underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);

    }

    @Test
    public void MultiplBooksCreated(){
        //creates one author that has multiple books
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor2();

        //book 2
        BookEntity bookEntity2 = TestDataUtil.createTestBook2(authorEntity);
        bookEntity2 = underTest.save(bookEntity2);

        //book 3
        BookEntity bookEntity3 = TestDataUtil.createTestBook3(authorEntity);
        bookEntity3 = underTest.save(bookEntity3);

       Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(bookEntity2, bookEntity3);

    }

    @Test
    public void updateBooks(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor3();

        BookEntity bookEntity4 = TestDataUtil.createTestBook4(authorEntity);
        underTest.save(bookEntity4);

        bookEntity4.setTitle("Throne of Glass");
        bookEntity4 = underTest.save(bookEntity4);

        Optional<BookEntity> result = underTest.findById(bookEntity4.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity4);

    }

    @Test
    public void BookDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();

        BookEntity bookEntity = TestDataUtil.createTestBook(authorEntity);
        bookEntity = underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();
    }


}
