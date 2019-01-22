package com.ex.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.ex.seckill.common.KeyPrefix;
import com.ex.seckill.redis.JedisClient;
import com.ex.seckill.service.RedisService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService{

    @Resource(name="jedisClientCluster")
    private JedisClient jedisClient;

    public <T> T get(KeyPrefix prefix,String key, Class<T> clazz){
        String value=jedisClient.get(prefix.getPrefix()+key);
        T t =stringToBean(value,clazz);
        return t;
    }

    public <T> boolean set(KeyPrefix prefix,String key,T value){
        String str=beanToString(value);
        if(StringUtils.isEmpty(str)||str.length()<=0){
            return false;
        }
        if(prefix.expireSeconds()<=0){
            jedisClient.set(prefix.getPrefix()+key,str);
        }else{
            jedisClient.setex(prefix.getPrefix()+key,prefix.expireSeconds()==-1?-1:prefix.expireSeconds(),str);
        }
        return true;
    }

    public <T> boolean exist(KeyPrefix prefix,String key){
        return jedisClient.exists(prefix+key);
    }

    public <T> Long delete(KeyPrefix prefix,String key){
        return jedisClient.del(prefix+key);
    }

    public <T> Long incr(KeyPrefix prefix,String key){
        return jedisClient.incr(prefix+key);
    }
    public <T> Long decr(KeyPrefix prefix,String key){
        return jedisClient.decr(prefix+key);
    }

    private <T> String beanToString(T value) {
        if(value==null){
            return null;
        }
        Class<?> clazz=value.getClass();
        if(clazz==int.class||clazz==Integer.class){
            return ""+value;
        }else if(clazz==String.class){
            return (String)value;
        }else if(clazz==long.class||clazz==Long.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    private <T> T stringToBean(String value,Class<T> clazz) {
        if(StringUtils.isEmpty(value)||value.length()<=0||clazz==null){
            return null;
        }
        if(clazz==int.class||clazz==Integer.class){
            return (T) Integer.valueOf(value);
        }else if(clazz==String.class){
            return (T)value;
        }else if(clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(value);
        }else{
            return JSON.toJavaObject(JSON.parseObject(value),clazz);
        }
    }

}
