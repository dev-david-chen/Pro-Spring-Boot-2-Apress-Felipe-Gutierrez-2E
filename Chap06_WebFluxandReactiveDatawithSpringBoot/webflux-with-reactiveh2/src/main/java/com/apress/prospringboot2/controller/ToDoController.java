package com.apress.prospringboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

import com.apress.prospringboot2.domain.ToDo;
import com.apress.prospringboot2.repository.ToDoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
@RequestMapping("/api")
class ToDoController {
    private ToDoRepository repository;

    @Autowired
    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    Flux<ToDo> getAll() {
        return repository.findAll();
    }

    @PostMapping("/todo")
    Mono<ToDo> addTodo(@RequestBody ToDo todo) {
        return repository.save(todo);
    }

    @PutMapping("/todo")
    Mono<ToDo> updateTodo(@RequestBody ToDo todo) {
        return repository.save(todo);
    }

    @DeleteMapping("/todo/{id}")
    Mono<Void> deleteById(@PathVariable("id") String id) {
        return repository.deleteById(id);
    }
}