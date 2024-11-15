package org.example.memoaserver.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(setStandaloneConfiguration());
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> tokenRedisTemplate() {
        return setRedisTemplate(0);
    }

    @Bean
    public RedisTemplate<String, Object> authCodeRedisTemplate() {
        return setRedisTemplate(1);
    }

    @Bean
    public RedisTemplate<String, Object> authenticEmailRedisTemplate() {
        return setRedisTemplate(2);
    }

    @Bean
    public RedisTemplate<String, Object> RateLimiterRedisTemplate() {
        return setRedisTemplate(3);
    }

    private RedisTemplate<String, Object> setRedisTemplate(int schema) {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(setStandaloneConfiguration());
        factory.setDatabase(schema);
        factory.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    private RedisStandaloneConfiguration setStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);
        return redisStandaloneConfiguration;
    }
}
