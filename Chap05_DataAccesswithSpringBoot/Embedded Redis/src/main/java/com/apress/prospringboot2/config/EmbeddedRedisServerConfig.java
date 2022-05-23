package com.apress.prospringboot2.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import redis.embedded.RedisServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedRedisServerConfig {

    RedisServer redisServer;

    public EmbeddedRedisServerConfig() {
    	//redisServer = new RedisServer(); 
    	redisServer =RedisServer.builder().setting("maxheap 128M").build();
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }
    
    @Bean
    RedisServer get() {
		return redisServer;
    }
    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}