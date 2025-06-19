package com.lau.Database.Services;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    public AuthorEntity  createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}
