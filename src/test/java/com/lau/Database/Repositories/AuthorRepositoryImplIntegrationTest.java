package com.lau.Database.Repositories;

import com.lau.Database.TestDataUtil;
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
public class AuthorRepositoryImplIntegrationTest {


    private AuthorRepository underTest;


    // The autowired annotation tells spring to inject dependencies as declared in this constructor
    @Autowired
    public AuthorRepositoryImplIntegrationTest(AuthorRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testAuthorCreation(){

        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
       Optional<Author> result =  underTest.findById(author.getId());
       assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void MultipleAuthorsCreated(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
        Author author2 = TestDataUtil.createTestAuthor2();
        underTest.save(author2);
        Author author3 = TestDataUtil.createTestAuthor3();
        underTest.save(author3);

        Iterable<Author> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(author, author2, author3);

    }

    @Test
    public void AuthorUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);

        author.setName("Emily Dickens");
        underTest.save( author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);


    }


    @Test
    public void AuthorDelete(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
        underTest.deleteById(author.getId());
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void AuthorsAgeLessThan(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
        Author author2 = TestDataUtil.createTestAuthor2();
        underTest.save(author2);
        Author author3 = TestDataUtil.createTestAuthor3();
        underTest.save(author3);
        Iterable<Author> result = underTest.AgeLessThan(50);

        assertThat(result).containsExactly(author2, author3);
    }

    @Test
    public void AuthorsWithAgeGreaterThan(){
        Author author = TestDataUtil.createTestAuthor();
        underTest.save(author);
        Author author2 = TestDataUtil.createTestAuthor2();
        underTest.save(author2);
        Author author3 = TestDataUtil.createTestAuthor3();
        underTest.save(author3);
        Iterable<Author> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result).containsExactly(author);
    }


}
