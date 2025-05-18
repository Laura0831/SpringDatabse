package com.lau.Database.dao;


import com.lau.Database.TestDataUtil;
import com.lau.Database.dao.impl.AuthorDaoImpl;
import com.lau.Database.dao.impl.BookDaoImpl;
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
public class BookDaoImplIntegrationTest {

    private BookDaoImpl underTest;
    private AuthorDAO author;

    // The autowired annotation tells spring to inject dependencies as declared in this constructor
    @Autowired
    public BookDaoImplIntegrationTest(BookDaoImpl underTest, AuthorDAO author){
        this.underTest = underTest;
        this.author = author;
    }

    @Test
    public void testBookCreation(){

        Author authorTemp = TestDataUtil.createTestAuthor(); //we got to create an author to be allowed access to the foreign key that is connected to the author
        author.create(authorTemp); //creates the author in the database
        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(authorTemp.getId()); //interconnects the author with the book
        underTest.create(book);
        Optional<Book> result =  underTest.find(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void MultiplBooksCreated(){
        //creates one author that has multiple books
        Author authorTemp = TestDataUtil.createTestAuthor2();
        author.create(authorTemp);

        //book 2
        Book book2 = TestDataUtil.createTestBook2();
        book2.setAuthorId(authorTemp.getId());
        underTest.create(book2);

        //book 3
        Book book3 = TestDataUtil.createTestBook3();
        book3.setAuthorId(authorTemp.getId());
        underTest.create(book3);

        List<Book> result = underTest.findMany();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(book2, book3);

    }

    @Test
    public void updateBooks(){
        Author authorTemp = TestDataUtil.createTestAuthor3();
        author.create(authorTemp);

        Book book4 = TestDataUtil.createTestBook4();
        book4.setAuthorId(authorTemp.getId());
        underTest.create(book4);

        book4.setTitle("Throne of Glass");
        underTest.update(book4.getIsbn(), book4);

        Optional<Book> result = underTest.find(book4.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book4);

    }

    @Test
    public void BookDeleted(){
        Author authorTemp = TestDataUtil.createTestAuthor();
        author.create(authorTemp);

        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(authorTemp.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());
        Optional<Book> result = underTest.find(book.getIsbn());
        assertThat(result).isEmpty();
    }


}
