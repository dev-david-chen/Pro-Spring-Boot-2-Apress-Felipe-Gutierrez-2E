package com.apress.prospringboot2.domain.MonoAndFluxExample;

import java.time.Duration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apress.prospringboot2.domain.ToDo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@Configuration
@Slf4j
public class MonoExample {

    @Bean
    public CommandLineRunner runMonoExample(){
        return args -> {

        	Sinks.One<ToDo> promise=Sinks.one();
            //MonoProcessor<ToDo> promise = MonoProcessor.create();

            Mono<ToDo> result = promise.asMono()
                    .doOnSuccess(p -> log.info("MONO >> ToDo: {}", p.getDescription()))
                    .doOnTerminate( () -> log.info("MONO >> Done"))
                    .doOnError(t -> log.error(t.getMessage(), t))
                    .subscribeOn(Schedulers.single());

            promise.emitValue(new ToDo("Buy my ticket for SpringOne Platform 2018"), null);//.onNext();
            //promise.onError(new IllegalArgumentException("There is an error processing the ToDo..."));

            result.block(Duration.ofMillis(1000));
        };
    }
}