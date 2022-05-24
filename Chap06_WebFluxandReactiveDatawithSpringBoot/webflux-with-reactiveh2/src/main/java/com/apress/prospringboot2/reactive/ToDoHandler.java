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

    public Mono<ServerResponse> getToDos(ServerRequest request) {
        Flux<ToDo> toDos = this.repository.findAll();
        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(toDos, ToDo.class);
    }
    
 

    public Mono<ServerResponse> getToDo(ServerRequest request) {
        return findById(request.pathVariable("id"));
    }



    public Mono<ServerResponse> newToDo(ServerRequest request) {
        Mono<ToDo> toDo = request.bodyToMono(ToDo.class);

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(toDo.flatMap(this::save),ToDo.class);
    }

    private Mono<ServerResponse> findById(String id){
        Mono<ToDo> toDo = this.repository.findById(id);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return toDo
                .flatMap(t -> ServerResponse
                        .ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(t),ToDo.class))
                .switchIfEmpty(notFound);
    }


    private Mono<ToDo> save(ToDo toDo) {
        return Mono.fromSupplier(
                () -> {
                    repository
                            .save(toDo)
                            .subscribe();
                    return toDo;
                });
    }
    
}
