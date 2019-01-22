package com.ex.seckill.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 通过@configuration注解里面的@Bean把JedisCluster注入到SPringle容器中
 * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
 */
//https://blog.csdn.net/u014199143/article/details/80692685
@Configuration
public class JedisClusterConfig {

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisCluster getJedisCluster(){
        //获取节点
        String[] servers = redisConfig.getClusterNodes().split(",");
        Set<HostAndPort> nodes=new HashSet<>();
        for(String server:servers){
            String[] ipPort=server.split(":");
            nodes.add(new HostAndPort(ipPort[0].trim(),Integer.valueOf(ipPort[1].trim())));
        }
        GenericObjectPoolConfig genericObjectPoolConfig=new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisConfig.getMinIdle());
        genericObjectPoolConfig.setMaxWaitMillis(redisConfig.getMaxWait());
        genericObjectPoolConfig.setMaxTotal(redisConfig.getMaxActive());
        return new JedisCluster(nodes,redisConfig.getCommandTimeout(),redisConfig.getTimeout(),redisConfig.getMaxAttempts(),genericObjectPoolConfig);
    }

}
