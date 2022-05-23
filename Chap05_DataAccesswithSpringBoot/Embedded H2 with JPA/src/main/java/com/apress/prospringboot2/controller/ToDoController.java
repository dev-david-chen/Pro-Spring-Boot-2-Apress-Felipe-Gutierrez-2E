package com.apress.prospringboot2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apress.prospringboot2.domain.ToDo;
import com.apress.prospringboot2.domain.ToDoBuilder;
import com.apress.prospringboot2.repository.ToDoRepository;
import com.apress.prospringboot2.validation.ToDoValidationError;
import com.apress.prospringboot2.validation.ToDoValidationErrorBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ToDoController {
    private static Logger log = LoggerFactory.getLogger(ToDoController.class);
    private ToDoRepository toDoRepository;

    @Autowired
    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getToDos(){
        return ResponseEntity.ok(toDoRepository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id){
        Optional<ToDo> toDo =
                toDoRepository.findById(id);
        if(toDo.isPresent())
         return ResponseEntity.ok(toDo.get());

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id){
        Optional<ToDo> toDo = toDoRepository.findById(id);
        if(!toDo.isPresent())
            return ResponseEntity.notFound().build();

        ToDo result = toDo.get();
        result.setCompleted(true);
        toDoRepository.save(result);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.ok().header("Location",location.toString()).build();
    }

    @RequestMapping(value="/todo", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo, Errors errors){
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(e -> log.error(e.getObjectName() + " " + e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }

        ToDo result = toDoRepository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(result); //ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id){
        toDoRepository.delete(ToDoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo){
        toDoRepository.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        log.error(exception.getMessage());
        return new ToDoValidationError(exception.getMessage());
    }

}