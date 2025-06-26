package com.lau.Database.Services;

import com.lau.Database.domain.Entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    public AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity newAuthor);

    void delete(Long id);
}
