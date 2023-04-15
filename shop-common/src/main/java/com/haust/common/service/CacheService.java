package com.haust.common.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface CacheService {
    public void setValue(String key, Object value);
    public Mono<String> getValue(String key);
    public void setValueWithExp(String key, String value, Long expireTime);
    public void setValueWithPExp(String key, String value, Long pExpireTime);
    public Mono<Long> incrementBy(String key, long delta);
    public Mono<Boolean> setNx(String key, String value);
    public Mono<Boolean> hSet(String key, String field, Object value);
    public Mono<Boolean> hMset(String key, Map data);
    public Mono<String> hGet(String key, String field);
    public Mono<Map> hGetAll(String key);
    public Mono<Boolean> setExp(String key, Long expTime);
    public Mono<Long> delectKey(String key);
}
