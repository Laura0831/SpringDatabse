package com.lau.Database.Mappers;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.dto.AuthorDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


//The purpose of this class is to convert an object of AuthorEntity class (Databse object) to an object of
//AuthorDTO (plain data object) and vice versa
@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) { //Maps from database obj to a plain object
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) { //Maps from plain obj to a database obj
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
