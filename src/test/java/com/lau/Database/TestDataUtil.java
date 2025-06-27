package com.lau.Database;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;
import com.lau.Database.domain.dto.AuthorDto;
import com.lau.Database.domain.dto.BookDto;

public final class TestDataUtil {


    private TestDataUtil(){

    }

    public static AuthorEntity createTestAuthor() {
        return AuthorEntity.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorEntity createTestAuthor2() {
        return AuthorEntity.builder()
                .name("Emily Henry")
                .age(45)
                .build();
    }

    public static AuthorDto createTestAuthor2_DTO() {
        return AuthorDto.builder()
                .name("Emily Henry")
                .age(45)
                .build();
    }

    public static AuthorEntity createTestAuthor3() {
        return AuthorEntity.builder()
                .name("Sarah Mass")
                .age(32)
                .build();
    }

    public static AuthorDto createTestAuthor4DTO() {
        return AuthorDto.builder()
                .name("Elsie Silver")
                .age(25)
                .build();
    }





    public static BookEntity createTestBook(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .author(authorEntity)
                .build();
    }

    public static BookEntity createTestBook2(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-0-5934-4087-2")
                .title("Book Lovers")
                .author(authorEntity)
                .build();
    }
    public static BookEntity createTestBook3(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-9848-0675-8")
                .title("People we meet on Vacation")
                .author(authorEntity)
                .build();
    }
    public static BookEntity createTestBook4(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-6196-3518-0")
                .title("Acotar")
                .author(authorEntity)
                .build();
    }

    public static BookDto createTestBookDto(final AuthorDto authorDto) {
        return BookDto.builder()
                .isbn("978-1-6196-3518-0")
                .title("Acotar")
                .authorDto(authorDto)
                .build();
    }


}
