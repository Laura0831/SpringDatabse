package com.lau.Database;

import com.lau.Database.domain.Author;
import com.lau.Database.domain.Book;

public final class TestDataUtil {


    private TestDataUtil(){

    }

    public static Author createTestAuthor() {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthor2() {
        return Author.builder()
                .id(2L)
                .name("Emily Henry")
                .age(34)
                .build();
    }

    public static Author createTestAuthor3() {
        return Author.builder()
                .id(3L)
                .name("Sarah Mass")
                .age(32)
                .build();
    }



    public static Book createTestBook() {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .authorId(1L)
                .build();
    }

    public static Book createTestBook2() {
        return Book.builder()
                .isbn("978-0-5934-4087-2")
                .title("Book Lovers")
                .authorId(2L)
                .build();
    }
    public static Book createTestBook3() {
        return Book.builder()
                .isbn("978-1-9848-0675-8")
                .title("People we meet on Vacation")
                .authorId(2L)
                .build();
    }
    public static Book createTestBook4() {
        return Book.builder()
                .isbn("978-1-6196-3518-0")
                .title("Acotar")
                .authorId(3L)
                .build();
    }
}
