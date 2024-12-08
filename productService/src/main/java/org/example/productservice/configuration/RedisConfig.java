package org.example.productservice.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY
        );

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .withCacheConfiguration("publisher-detail-by-id", createRedisCacheConfiguration(10, "m"))
                .withCacheConfiguration("list-publisher-detail-in-page", createRedisCacheConfiguration(5, "h"))
                .withCacheConfiguration("author-detail-by-id", createRedisCacheConfiguration(10, "h"))
                .build();
    }

    RedisCacheConfiguration createRedisCacheConfiguration(int expire, String unit) {
        Duration ttl;
        if (unit.equalsIgnoreCase("s")) {
            ttl = Duration.ofSeconds(expire);
        } else if (unit.equalsIgnoreCase("m")) {
            ttl = Duration.ofMinutes(expire);
        } else if (unit.equalsIgnoreCase("h")) {
            ttl = Duration.ofHours(expire);
        } else if (unit.equalsIgnoreCase("d")) {
            ttl = Duration.ofDays(expire);
        } else {
            // default milliseconds
            ttl = Duration.ofMillis(expire);
        }

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(ttl)
                .disableCachingNullValues();
    }
}
