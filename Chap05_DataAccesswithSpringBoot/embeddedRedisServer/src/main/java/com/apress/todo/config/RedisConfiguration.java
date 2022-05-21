package com.apress.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import redis.embedded.RedisServer;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisServer server) {
        return new LettuceConnectionFactory("localhost",6379);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}