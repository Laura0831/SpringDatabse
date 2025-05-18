package com.lau.Database;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}
}
//public class DatabaseApplication implements CommandLineRunner {
//
//
//	//one function
//	private final DataSource dataSource;
//
//	public DatabaseApplication(final DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
//
//
//	//main function
//	public static void main(String[] args) {
//		SpringApplication.run(DatabaseApplication.class, args);
//	}
//
//
//	//second function
//	@Override
//	public void run(final String... args) {
//		//log.info("Datasource: " + dataSource.toString());
//		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
//		restTemplate.execute("select 1");
//	}
//
//}