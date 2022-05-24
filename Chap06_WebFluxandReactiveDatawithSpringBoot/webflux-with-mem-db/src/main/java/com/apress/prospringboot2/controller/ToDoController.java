package com.apress.prospringboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;

import com.apress.prospringboot2.domain.ToDo;
import com.apress.prospringboot2.repository.ToDoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
public class ToDoController {
	@Autowired
    private ToDoRepository repository;

    public ToDoController(ToDoRepository repository){
        this.repository = repository;
    }

    @GetMapping("/todo/{id}")
    public Mono<ToDo> getToDo(@PathVariable String id){
        return this.repository.findById(id);
    }

    @GetMapping("/todo")
    public Flux<ToDo> getToDos(){
        return this.repository.findAll();
    }

}
