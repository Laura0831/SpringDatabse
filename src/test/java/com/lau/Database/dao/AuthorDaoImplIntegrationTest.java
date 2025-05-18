package com.lau.Database.dao;

import com.lau.Database.TestDataUtil;
import com.lau.Database.dao.impl.AuthorDaoImpl;
import com.lau.Database.domain.Author;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //this allows for spring boot to remove and
// clean the database author creation after every test case is run. This prevents multiple creation of the same author
public class AuthorDaoImplIntegrationTest {


    private AuthorDaoImpl underTest;


    // The autowired annotation tells spring to inject dependencies as declared in this constructor
    @Autowired
    public AuthorDaoImplIntegrationTest(AuthorDaoImpl underTest){
        this.underTest = underTest;
    }

    @Test
    public void testAuthorCreation(){

        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
       Optional<Author> result =  underTest.findOne(author.getId());
       assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void MultipleAuthorsCreated(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        Author author2 = TestDataUtil.createTestAuthor2();
        underTest.create(author2);
        Author author3 = TestDataUtil.createTestAuthor3();
        underTest.create(author3);

        List<Author> result = underTest.find();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(author, author2, author3);

    }

    @Test
    public void AuthorUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        author.setName("Emily Dickens");
        underTest.update(author.getId(), author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);


    }


    @Test
    public void AuthorDelete(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.create(author);
        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isEmpty();
    }


}
