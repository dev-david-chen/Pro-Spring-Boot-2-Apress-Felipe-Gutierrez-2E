package com.apress.prospringboot2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apress.prospringboot2.repository.ToDoRepository;

@Configuration
public class ToDoConfig {

    @Bean
    public ToDoRepository repository(){
        return new ToDoRepository();
    }
}
