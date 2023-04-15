package com.haust.common.service.impl;

import com.haust.common.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheServiceImpl implements CacheService {
    private ReactiveRedisTemplate redisTemplate;

    public void setRedisTemplate(ReactiveRedisTemplate reactiveRedisTemplate) {
        this.redisTemplate = reactiveRedisTemplate;
    }

    @Override
    public void setValue(String key, Object value) {
        //存储key-value数据
        redisTemplate.opsForValue().set(key, value).subscribe();
    }

    @Override
    public Mono<String> getValue(String key) {
        //根据key查询value
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setValueWithExp(String key, String value, Long expireTime) {
        Assert.isTrue(expireTime!=null&&expireTime>0,()->{
            throw new RuntimeException("时间不能小于0");
        });
        //存储key-value数据，并设置时间，单位秒
        redisTemplate.opsForValue().set(key,value, Duration.of(expireTime, ChronoUnit.SECONDS)).subscribe();
    }

    @Override
    public void setValueWithPExp(String key, String value, Long pExpireTime) {
        Assert.isTrue(pExpireTime!=null&&pExpireTime>0,()->{
            throw new RuntimeException("时间不能小于0");
        });
        //存储key-value数据，并设置时间，单位毫秒
        redisTemplate.opsForValue().set(key,value, Duration.of(pExpireTime, ChronoUnit.MILLIS)).subscribe();

    }

    @Override
    public Mono<Long> incrementBy(String key, long delta) {
        //根据key设置value+delta
        return redisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public Mono<Boolean> setNx(String key, String value) {
        Assert.hasText(key,()->{
            throw new RuntimeException("key不能为空");
        });
        //如果key-value存在则添加
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    @Override
    public Mono<Boolean> hSet(String key, String field, Object value) {
        //TODO 断言判断数据不能为空
        //添加hash-map的数据
        return redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public Mono<Boolean> hMset(String key, Map data) {
        return redisTemplate.opsForHash().putAll(key, data);
    }

    @Override
    public Mono<String> hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public Mono<Map> hGetAll(String key) {
        //获取数据
        Flux<Map.Entry<String,String>> entries = redisTemplate.opsForHash().entries(key);
        //将数据从flux转成mono再返回
        return entries.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Mono<Boolean> setExp(String key, Long expTime) {
        return redisTemplate.expire(key, Duration.ofMinutes(expTime));
    }

    @Override
    public Mono<Long> delectKey(String key) {
        return redisTemplate.delete(key);
    }
}
