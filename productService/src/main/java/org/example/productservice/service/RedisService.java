package org.example.productservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisService {

    void saveWithExpireTime(String key, Object value, int expire, TimeUnit unit);

    void save(String key, Object value);

    void saveForList(Map<String, Object> mapData);

    <T> T get(String key);

    <T> T get(Collection<String> keys);

    boolean delete(String key);

    long delete(List<String> key);

    boolean hasKey(String key);
}
