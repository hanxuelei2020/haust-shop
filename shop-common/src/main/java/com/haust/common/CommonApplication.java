package com.haust.common;

import com.haust.common.handler.LoginUserHandlerMethodArgumentResolver;
import com.haust.common.service.CacheService;
import com.haust.common.service.UserTokenManager;
import com.haust.common.service.impl.CacheServiceImpl;
import com.haust.common.service.impl.RedisUserTokenManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@SpringBootApplication
public class CommonApplication {
    // 不知什么原因,导致 common 中的 service 被识别为 mybatis的组件,这里对组件直接进行声明
    @Bean
    public CacheService cacheService(@Qualifier("reactiveRedisTemplate") ReactiveRedisTemplate redisTemplate) {
        CacheServiceImpl service = new CacheServiceImpl();
        service.setRedisTemplate(redisTemplate);
        // 注册组件
        return service;
    }

    @Bean
    public UserTokenManager userTokenManager(@Value("${dts.jwt.secret}") String jwtSecret, CacheService cacheService) {
        RedisUserTokenManager redisUserTokenManager = new RedisUserTokenManager();
        redisUserTokenManager.setCacheService(cacheService);
        redisUserTokenManager.setJwtSecret(jwtSecret);
        return redisUserTokenManager;
    }

    @Bean
    public LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver(UserTokenManager userTokenManager) {
        LoginUserHandlerMethodArgumentResolver resolver = new LoginUserHandlerMethodArgumentResolver();
        resolver.setUserTokenManager(userTokenManager);
        return resolver;
    }
}
