package com.example.kino;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.kino.controllers.KinoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class Spring1Application {

	public static void main(String[] args)  {
		SpringApplication.run(Spring1Application.class, args);
		
		
		
		
		
		
		
	}
	

}
