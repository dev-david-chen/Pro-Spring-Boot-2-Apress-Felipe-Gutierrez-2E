package com.apress.prospringboot2.repository;

import org.springframework.data.repository.CrudRepository;

import com.apress.prospringboot2.domain.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo,String> {
}