package com.apress.prospringboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude= {EmbeddedMongoAutoConfiguration.class})
public class Chap05DataAccesswithSpringBootMongoDbEmbeddedApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap05DataAccesswithSpringBootMongoDbEmbeddedApplication.class, args);
	}

}
