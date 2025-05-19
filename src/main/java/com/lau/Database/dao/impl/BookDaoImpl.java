package com.lau.Database.dao.impl;

import com.lau.Database.dao.BookDAO;
import com.lau.Database.domain.Author;
import com.lau.Database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDAO {


    private JdbcTemplate jdbcTemp;

    public BookDaoImpl (JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    @Override
    public void create(Book book) {
        jdbcTemp.update(
                "INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId()
        );
    }



    @Override
    public Optional<Book> find(String isbn) {
        List<Book> result = jdbcTemp.query("SELECT isbn, title, author_id FROM books WHERE isbn= ? LIMIT 1",
                new BookRowMapper(), isbn);

        return result.stream().findFirst();
    }

    @Override
    public List<Book> findMany() {
        return jdbcTemp.query("SELECT isbn, title, author_id FROM books",
                new BookDaoImpl.BookRowMapper());
    }

    @Override
    public void update(String isbn, Book book) {

        jdbcTemp.update( "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
               book.getIsbn(),
               book.getTitle(),
               book.getAuthorId(),
               isbn
       );
    }

    @Override
    public void delete(String isbn) {
        jdbcTemp.update("DELETE FROM books WHERE isbn = ?", isbn);
    }


    //NEW CLASS INSIDE:
    //rowMapper is there to convert from a result set (which is something that is return when we query the database)
    //and convert it into an object (Book object)
    public static class BookRowMapper implements RowMapper<Book> {

        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }



}
