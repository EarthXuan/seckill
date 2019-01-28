package com.ex.seckill.service;

import com.ex.seckill.common.KeyPrefix;

public interface RedisService {

    <T> T get(KeyPrefix prefix,String key, Class<T> clazz);

    <T> boolean set(KeyPrefix prefix,String key,T value);

    <T> boolean exist(KeyPrefix prefix,String key);
    <T> Long delete(KeyPrefix prefix,String key);
    boolean delete(KeyPrefix prefix);
    <T> Long incr(KeyPrefix prefix,String key);
    <T> Long decr(KeyPrefix prefix, String key);
}
