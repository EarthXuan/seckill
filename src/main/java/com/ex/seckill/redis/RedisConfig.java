package com.ex.seckill.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
//https://blog.csdn.net/lucyTheSlayer/article/details/80430912
@ConditionalOnClass({JedisCluster.class})
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String clusterNodes;
    private int maxActive;
    private int maxWait;
    private int maxIdle;
    private int minIdle;
    private int timeout;
    private int commandTimeout;
    private int maxAttempts;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(int commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}

