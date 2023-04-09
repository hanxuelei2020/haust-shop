package com.haust.service.service.third;

import com.haust.service.domain.user.DtsUserFormid;

public interface DtsFormUserIdService {
    DtsUserFormid queryByOpenId(String openId);

    int updateUserFormId(DtsUserFormid userFormid);

    void addUserFormid(DtsUserFormid userFormid);
}