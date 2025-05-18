package com.lau.Database.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {


    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource){ //we can now use this to query our database 
        return new JdbcTemplate(dataSource);
    }


}
