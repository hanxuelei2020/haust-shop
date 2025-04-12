package com.haust.common.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.haust.common.config.RedisPrefix;
import com.haust.common.model.UserToken;
import com.haust.common.service.CacheService;
import com.haust.common.service.UserTokenManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// token 是每个服务都需要的,如果不在网关中做的话,就需要在common中做
// 后续会放到网关中
public class RedisUserTokenManager implements UserTokenManager {

    private String jwtSecret;
    private CacheService cacheService;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public Integer getUserId(String token) {
        // 尝试通过 token 获取 userId
        String userToken = null;
        List<String> userId = JWT.decode(token).getAudience();
        if (userId != null && !userId.isEmpty()) {
            if (userId.size() == 1) {
                userToken = cacheService.getValue(RedisPrefix.USER_ID_TOKEN + userId.get(0)).block();
            }
        }

        // 如果用户传递进来的 token 并不能解析出来对应的 userid, 那么直接返回
        if (userToken == null || !userToken.equals(token)) {
            return null;
        }

        return Integer.parseInt(userId.get(0));
    }
    @Override
    public UserToken generateToken(Integer id, String sessionKey) {
        UserToken userToken = new UserToken();

        // 更新时间和过期时间
        Instant update = Instant.now();
        Instant expires = update.plus(30, ChronoUnit.MINUTES);
        LocalDateTime expireDateTime = LocalDateTime.ofInstant(expires, ZoneId.systemDefault());
        // 生成对应的 token
        JWTCreator.Builder builder = JWT.create();
        // 三十分钟之后失效
        String token = builder.withExpiresAt(expires)
                .withClaim("sessionKey", sessionKey)
                .withAudience(String.valueOf(id))
                .withIssuedAt(update)
                .sign(Algorithm.HMAC256(jwtSecret));

        userToken.setToken(token);
        userToken.setUpdateTime(LocalDateTime.ofInstant(update, ZoneId.systemDefault()));
        userToken.setExpireTime(expireDateTime);
        userToken.setUserId(id);
        // 将 id 和 token 的对应关系放入 redis
        cacheService.setValueWithExp(RedisPrefix.USER_ID_TOKEN + id, token,
                30 * 60L);

        return userToken;
    }
    @Override
    public String getSessionKey(Integer userId) {
        Optional<String> res = cacheService.getValue(RedisPrefix.USER_ID_TOKEN + userId)
                .filter(Objects::nonNull)
                .map(t -> JWT.decode(t).getClaim("sessionKey").asString())
                .blockOptional();

        return res.orElse(null);
    }
    @Override
    public void removeToken(Integer userId) {
        cacheService.delectKey(RedisPrefix.USER_ID_TOKEN + userId);
    }
}
