package com.ex.seckill.redis;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public interface JedisClient {
    public String set(String key, String value);

    public String setex(String key,int seconds, String value);

    public String get(String key);

    public Long del(String key);
    public Long del(String[] key);

    public Long hset(String key, String item, String value);

    public String hget(String key, String item);

    public Long incr(String key);

    public Long decr(String key);

    public Long expire(String key, int second);

    public Long ttl(String key);

    public Long hdel(String key, String item);

    public Boolean exists(String key);

    ScanResult<String> scan(String cursor, ScanParams sp);
}
