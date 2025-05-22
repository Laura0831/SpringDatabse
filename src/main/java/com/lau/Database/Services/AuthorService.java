package com.lau.Database.Services;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.dto.AuthorDto;

public interface AuthorService {

    public AuthorEntity  createAuthor(AuthorEntity authorEntity);

}
