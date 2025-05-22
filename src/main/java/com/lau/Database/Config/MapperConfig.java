package com.lau.Database.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){ //Now we have a modelMapper bean that can be injected in multiple parts of the application
        return new ModelMapper();
    }

}
