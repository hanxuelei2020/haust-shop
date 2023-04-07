package com.haust.shop.user.service.impl;

import com.haust.service.domain.user.DtsUser;
import com.haust.shop.user.model.UserInfo;
import com.haust.shop.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@DubboService
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private DtsUserServiceImpl userService;

	public UserInfo getInfo(Integer userId) {
		DtsUser user = userService.findById(userId);
		Assert.state(user != null, "用户不存在");
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName(user.getNickname());
		userInfo.setAvatarUrl(user.getAvatar());
		return userInfo;
	}
}
