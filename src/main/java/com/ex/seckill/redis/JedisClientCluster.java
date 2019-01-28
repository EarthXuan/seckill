package com.ex.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * redis客户端集群版实现类
 * 使用jedisPool可以关闭，但是jediscluster不能关，关闭了就不能使用了
 */
@Component("jedisClientCluster")
public class JedisClientCluster implements JedisClient {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public String setex(String key,int seconds, String value) {
        return jedisCluster.setex(key,seconds,value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public Long del(String key) {
        return jedisCluster.del(key);
    }
    @Override
    public Long del(String[] keys) {
        return jedisCluster.del(keys);
    }

    @Override
    public Long hset(String key, String item, String value) {
        return jedisCluster.hset(key, item, value);
    }

    @Override
    public String hget(String key, String item) {
        return jedisCluster.hget(key, item);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public Long decr(String key) {
        return jedisCluster.decr(key);
    }

    @Override
    public Long expire(String key, int second) {
        return jedisCluster.expire(key, second);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Long hdel(String key, String item) {
        return jedisCluster.hdel(key, item);
    }

    @Override
    public Boolean exists(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public ScanResult<String> scan(String cursor, ScanParams sp) {
        return jedisCluster.scan(cursor,sp);
    }
}
