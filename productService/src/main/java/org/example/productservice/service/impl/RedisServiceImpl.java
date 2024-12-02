package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
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
    public void saveForList(Map<String, Object> mapData) {
        redisTemplate.opsForValue().multiSet(mapData);
    }

    @Override
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
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
