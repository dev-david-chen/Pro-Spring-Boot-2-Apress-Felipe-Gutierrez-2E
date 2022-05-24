package com.apress.prospringboot2.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.apress.prospringboot2.domain.ToDo;

public interface ToDoRepository extends ReactiveCrudRepository<ToDo, String> {

}