package com.haust.shop.third.service.impl;

import com.haust.service.domain.user.DtsUserFormid;
import com.haust.service.domain.user.DtsUserFormidExample;
import com.haust.service.service.third.DtsFormUserIdService;
import com.haust.shop.third.mapper.DtsUserFormidMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@DubboService
public class DtsUserFormIdServiceImpl implements DtsFormUserIdService {
	@Autowired
	private DtsUserFormidMapper formidMapper;

	/**
	 * 查找是否有可用的FormId
	 *
	 * @param openId
	 * @return
	 */
	public DtsUserFormid queryByOpenId(String openId) {
		DtsUserFormidExample example = new DtsUserFormidExample();
		// 符合找到该用户记录，且可用次数大于1，且还未过期
		example.or().andOpenidEqualTo(openId).andExpireTimeGreaterThan(LocalDateTime.now());
		return formidMapper.selectOneByExample(example);
	}

	/**
	 * 更新或删除FormId
	 *
	 * @param userFormid
	 */
	public int updateUserFormId(DtsUserFormid userFormid) {
		// 更新或者删除缓存
		if (userFormid.getIsprepay() && userFormid.getUseamount() > 1) {
			userFormid.setUseamount(userFormid.getUseamount() - 1);
			userFormid.setUpdateTime(LocalDateTime.now());
			return formidMapper.updateByPrimaryKey(userFormid);
		} else {
			return formidMapper.deleteByPrimaryKey(userFormid.getId());
		}
	}

	/**
	 * 添加一个 FormId
	 *
	 * @param userFormid
	 */
	public void addUserFormid(DtsUserFormid userFormid) {
		userFormid.setAddTime(LocalDateTime.now());
		userFormid.setUpdateTime(LocalDateTime.now());
		formidMapper.insertSelective(userFormid);
	}
}
