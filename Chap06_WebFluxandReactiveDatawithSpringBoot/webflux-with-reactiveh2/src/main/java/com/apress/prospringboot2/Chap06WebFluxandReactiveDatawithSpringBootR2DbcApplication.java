package com.apress.prospringboot2;

import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;

import com.apress.prospringboot2.domain.ToDo;
import com.apress.prospringboot2.repository.ToDoRepository;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class Chap06WebFluxandReactiveDatawithSpringBootR2DbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap06WebFluxandReactiveDatawithSpringBootR2DbcApplication.class, args);
	}
	@Bean
    ApplicationRunner init(ToDoRepository repository, DatabaseClient client) {
       return args -> {
            client.sql("create table IF NOT EXISTS TODO" +
                    "(id varchar(36) not null,\n"
                    + "  description varchar(255) not null,\n"
                    + "  created timestamp,\n"
                    + "  modified timestamp,\n"
                    + "  completed boolean,"
                    + "  newtodo boolean \n"
                    + "  );")
                   .fetch().first().subscribe();
            client.sql("DELETE FROM TODO;").fetch().first().subscribe();

            Stream<ToDo> stream = Stream.of(new ToDo("Do homework"),
            		new ToDo("Workout in the mornings", true),
            		new ToDo("Make dinner tonight"),
            		new ToDo("Clean the studio", true));

            // initialize the database

            repository.saveAll(Flux.fromStream(stream))
                    .then()
                    .subscribe(); // execute
        	};
//        	return args -> {
//        		repository.save(new ToDo("Do homework")).subscribe();
//        		repository.save(new ToDo("Workout in the mornings", true)).
//        		subscribe();
//        		repository.save(new ToDo("Make dinner tonight")).subscribe();
//        		repository.save(new ToDo("Clean the studio", true)).subscribe();
//        		repository.findAll().subscribe(System.out::println);
//        		};
 
    }
}
