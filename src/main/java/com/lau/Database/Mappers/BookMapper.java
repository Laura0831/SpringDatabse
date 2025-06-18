package com.lau.Database.Mappers;

import com.lau.Database.domain.Entity.AuthorEntity;
import com.lau.Database.domain.Entity.BookEntity;
import com.lau.Database.domain.dto.AuthorDto;
import com.lau.Database.domain.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//The purpose of this class is to convert an object of BookEntity class (Database object) to an object of
//BookDTO (plain data object) and vice versa
@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {//Maps from database obj (Entity) to a plain object (DTO)
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {//Maps from plain obj (DTO) to a database obj (Entity)
        return modelMapper.map(bookDto, BookEntity .class);
    }
}
