package com.apress.prospringboot2.domain.MonoAndFluxExample;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apress.prospringboot2.domain.ToDo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;
import reactor.core.scheduler.Schedulers;

@Configuration
@Slf4j
public class FluxExample {

	@Bean
	public CommandLineRunner runFluxExample() {
		return args -> {

			// EmitterProcessor<ToDo> stream = EmitterProcessor.create();
			Sinks.Many<ToDo> stream = Sinks.many().multicast().onBackpressureBuffer();
			Flux<ToDo> fluxView = stream.asFlux();

			Mono<List<ToDo>> promise = fluxView.filter(s -> s.isCompleted())
					.doOnNext(s -> log.info("FLUX >>> ToDo: {}", s.getDescription()))
					.doOnTerminate( ()-> log.info("FLUX >> Done"))
                    .doOnError(t -> log.error(t.getMessage(), t))
					.collectList()
					.subscribeOn(Schedulers.single());

			stream.emitNext(new ToDo("Read a Book", true), EmitFailureHandler.FAIL_FAST);
			stream.emitNext(new ToDo("Listen Classical Music", true), EmitFailureHandler.FAIL_FAST);
			stream.emitNext(new ToDo("Workout in the Mornings"), EmitFailureHandler.FAIL_FAST);
			stream.emitNext(new ToDo("Organize my room", true), EmitFailureHandler.FAIL_FAST);
			stream.emitNext(new ToDo("Go to the Car Wash", true), EmitFailureHandler.FAIL_FAST);
			stream.emitNext(new ToDo("SP1 2018 is coming", true), EmitFailureHandler.FAIL_FAST);

			stream.emitComplete(EmitFailureHandler.FAIL_FAST);
			promise.block();

		};
	}
}
