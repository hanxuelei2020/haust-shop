package com.haust.service.service.user;

import com.haust.service.domain.user.DtsUserFormid;

public interface DtsUserFormIdService {
    DtsUserFormid queryByOpenId(String openId);

    int updateUserFormId(DtsUserFormid userFormid);

    void addUserFormid(DtsUserFormid userFormid);
}