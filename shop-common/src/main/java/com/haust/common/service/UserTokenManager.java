package com.haust.common.service;

import com.haust.common.model.UserToken;

public interface UserTokenManager {
    public Integer getUserId(String token);
    public UserToken generateToken(Integer id, String sessionKey);
    public String getSessionKey(Integer userId);
    public void removeToken(Integer userId);
}
