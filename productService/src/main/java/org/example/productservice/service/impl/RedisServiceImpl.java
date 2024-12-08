package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.service.RedisService;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RedisServiceImpl implements RedisService {
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveWithExpireTime(String key, Object value, int expire, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, expire, unit);
    }

    @Override
    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void savePipeline(Map<String, Object> data, int expire, TimeUnit timeUnit) {
        long ttlMillis = timeUnit.toMillis(expire);
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            RedisSerializer<Object> serializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                byte[] serializedValue = serializer.serialize(value);
                redisConnection.set(
                        key.getBytes(),
                        serializedValue,
                        Expiration.milliseconds(ttlMillis),
                        RedisStringCommands.SetOption.UPSERT);
            }
            return null;
        });
    }


    @Override
    public void saveForList(Map<String, Object> mapData) {
        redisTemplate.opsForValue().multiSet(mapData);
    }

    @Override
    public <T> T get(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T> T get(Collection<String> keys) {
        return (T) redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
