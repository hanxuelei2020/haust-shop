package com.haust.common.serialization;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

// 编写序列化配置文件
@Component
public class MyRedisSerializationContext implements RedisSerializationContext {
    private StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    private GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

    //设置key的序列化方式
    @Override
    public SerializationPair getKeySerializationPair() {
        return SerializationPair.fromSerializer(stringRedisSerializer);
    }

    //设置value的序列化方式
    @Override
    public SerializationPair getValueSerializationPair() {
        return SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer);
    }

    //设置hashkey的序列化方式
    @Override
    public SerializationPair getHashKeySerializationPair() {
        return SerializationPair.fromSerializer(stringRedisSerializer);
    }

    //设置hashvalue的序列化方式
    @Override
    public SerializationPair getHashValueSerializationPair() {
        return SerializationPair.fromSerializer(genericJackson2JsonRedisSerializer);
    }

    //设置spring字符串的序列化方式
    @Override
    public SerializationPair<String> getStringSerializationPair() {
        return SerializationPair.fromSerializer(stringRedisSerializer);
    }
}