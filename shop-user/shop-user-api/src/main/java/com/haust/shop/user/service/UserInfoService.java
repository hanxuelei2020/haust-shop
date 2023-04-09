package com.haust.shop.user.service;

import com.haust.shop.user.model.UserInfo;

/**
 * 如果是用户独有的,其他不会调用也不会修改的,那么就不用放在公共中
 * 放在公共的里面后续只会增加拆分的负担,增大耦合性
 */
public interface UserInfoService {
    public UserInfo getInfo(Integer userId);
}
