package com.lau.Database.Repositories;

import com.lau.Database.TestDataUtil;
import com.lau.Database.domain.Entity.AuthorEntity;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //this allows for spring boot to remove and
// clean the database author creation after every test case is run. This prevents multiple creation of the same author
public class AuthorEntityRepositoryImplIntegrationTest {


    private AuthorRepository underTest;


    // The autowired annotation tells spring to inject dependencies as declared in this constructor
    @Autowired
    public AuthorEntityRepositoryImplIntegrationTest(AuthorRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testAuthorCreation(){

        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);
       Optional<AuthorEntity> result =  underTest.findById(authorEntity.getId());
       assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);

    }

    @Test
    public void MultipleAuthorsCreated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);
        AuthorEntity authorEntity2 = TestDataUtil.createTestAuthor2();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.createTestAuthor3();
        underTest.save(authorEntity3);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(authorEntity, authorEntity2, authorEntity3);

    }

    @Test
    public void AuthorUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);

        authorEntity.setName("Emily Dickens");
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);


    }


    @Test
    public void AuthorDelete(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);
        underTest.deleteById(authorEntity.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void AuthorsAgeLessThan(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);
        AuthorEntity authorEntity2 = TestDataUtil.createTestAuthor2();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.createTestAuthor3();
        underTest.save(authorEntity3);
        Iterable<AuthorEntity> result = underTest.findByAgeLessThan(50);

        assertThat(result).containsExactly(authorEntity2, authorEntity3);
    }

    @Test
    public void AuthorsWithAgeGreaterThan(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor();
        underTest.save(authorEntity);
        AuthorEntity authorEntity2 = TestDataUtil.createTestAuthor2();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.createTestAuthor3();
        underTest.save(authorEntity3);
        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result).containsExactly(authorEntity);
    }


}
