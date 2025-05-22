package com.lau.Database.Repositories;

import com.lau.Database.domain.Entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, String> {
}
