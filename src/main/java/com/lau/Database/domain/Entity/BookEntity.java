package com.lau.Database.domain.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL) //this means if we get a book back, we also get to retrieve the author and all the values in the author class
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;


}
