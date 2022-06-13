package com.pokedex.pokeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;

@Configuration
@SpringBootApplication
public class PokeApiApplication {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // as per documentation https://www.slf4j.org/faq.html#declared_static

	public static void main(String[] args) {
		logger.info("Starting up...");
		SpringApplication.run(PokeApiApplication.class, args);
	}

}
