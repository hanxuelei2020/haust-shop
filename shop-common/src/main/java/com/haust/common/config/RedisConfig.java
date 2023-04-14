package com.haust.common.config;

import com.haust.common.serialization.MyRedisSerializationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@Configuration
public class RedisConfig {
    /**
     * 创建模板对象，使用自定义的序列化方式
     * @param factory
     * @param context 我们手动指定的序列化方式
     * @return
     */
    @Bean
    public ReactiveRedisTemplate reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory,
            MyRedisSerializationContext context){
        return new ReactiveRedisTemplate(factory, context);
    }
}