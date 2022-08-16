package com.zhangbo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/*
* Redis的配置类
* */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String ,Object> redisTemplate = new RedisTemplate<>();

        //String类型序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //Strig 类型value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash类型，key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //Hash 类型value序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);

        return  redisTemplate;
    }

}