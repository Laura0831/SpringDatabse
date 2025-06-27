package com.lau.Database.Repositories;

import com.lau.Database.domain.Entity.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends CrudRepository<BookEntity, String>,
        PagingAndSortingRepository<BookEntity, String> {

    //Pagination means fetching the data in small chucks, like 10 books at a time
    //this helps your app be faster and user-friendly
    //Very useful for databases with hundreds or even thousands of books or data

    //Sorting means ordering your results based on one or more fields
    // is useful when added to paging as it sorts the small amount from Pagination in what ever order you ask
}
