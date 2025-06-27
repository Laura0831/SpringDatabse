package com.lau.Database.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){ //Now we have a modelMapper bean that can be injected in multiple parts of the application
        ModelMapper modelMapper = new ModelMapper();

        // which allows it to map nested objects between DTOs and entities,
        // so when a Book is created with a nested Author in JSON,
        // the Author is also mapped and can be persisted.
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }

}
