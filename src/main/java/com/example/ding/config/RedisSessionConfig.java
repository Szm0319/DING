package com.example.ding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.security.PublicKey;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3000)
public class RedisSessionConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 使用 StringRedisSerializer 来序列化键
        template.setKeySerializer(new StringRedisSerializer());

        // 使用 GenericJackson2JsonRedisSerializer 来序列化值
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 为哈希键和值也设置相应的序列化器
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
