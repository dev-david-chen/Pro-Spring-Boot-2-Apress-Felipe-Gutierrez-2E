package com.apress.prospringboot2.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.apress.prospringboot2.domain.ToDo;
import com.apress.prospringboot2.repository.ToDoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class ToDoHandler {

    private ToDoRepository repository;

    public ToDoHandler(ToDoRepository repository){
        this.repository = repository;
    }

    public Mono<ServerResponse> getToDo(ServerRequest request) {
        String toDoId = request.pathVariable("id");
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<ToDo> toDo = this.repository.findById(toDoId);

        return toDo.
                flatMap(t -> ServerResponse
                                    .ok()
                                    .contentType(APPLICATION_JSON)
                                    .body(Mono.just(t),ToDo.class)
                                    .switchIfEmpty(notFound));
    }

    public Mono<ServerResponse> getToDos(ServerRequest request) {
        Flux<ToDo> toDos = this.repository.findAll();
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(toDos, ToDo.class);
    }
}
