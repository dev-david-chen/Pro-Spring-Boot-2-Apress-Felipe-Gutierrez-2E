package com.apress.todo.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import redis.embedded.RedisServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedRedisServerConfig {

    RedisServer redisServer;

    public EmbeddedRedisServerConfig() {
    	//RedisServer.newServerFrom(null)
    	redisServer = new RedisServer();
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