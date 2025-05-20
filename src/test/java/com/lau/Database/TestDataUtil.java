package com.lau.Database;

import com.lau.Database.domain.Author;
import com.lau.Database.domain.Book;

public final class TestDataUtil {


    private TestDataUtil(){

    }

    public static Author createTestAuthor() {
        return Author.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthor2() {
        return Author.builder()
                .name("Emily Henry")
                .age(34)
                .build();
    }

    public static Author createTestAuthor3() {
        return Author.builder()
                .name("Sarah Mass")
                .age(32)
                .build();
    }



    public static Book createTestBook(final Author author) {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The shadow in the Attic")
                .author(author)
                .build();
    }

    public static Book createTestBook2(final Author author) {
        return Book.builder()
                .isbn("978-0-5934-4087-2")
                .title("Book Lovers")
                .author(author)
                .build();
    }
    public static Book createTestBook3(final Author author) {
        return Book.builder()
                .isbn("978-1-9848-0675-8")
                .title("People we meet on Vacation")
                .author(author)
                .build();
    }
    public static Book createTestBook4(final Author author) {
        return Book.builder()
                .isbn("978-1-6196-3518-0")
                .title("Acotar")
                .author(author)
                .build();
    }
}
