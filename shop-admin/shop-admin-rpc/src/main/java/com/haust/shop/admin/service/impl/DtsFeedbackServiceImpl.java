package com.haust.shop.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.admin.DtsFeedback;
import com.haust.service.domain.admin.DtsFeedbackExample;
import com.haust.service.service.admin.DtsFeedbackService;

import com.haust.shop.admin.mapper.DtsFeedbackMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 韩雪磊
 */
@Service
@DubboService
public class DtsFeedbackServiceImpl implements DtsFeedbackService {
	@Autowired
	private DtsFeedbackMapper feedbackMapper;

	public Integer add(DtsFeedback feedback) {
		feedback.setAddTime(LocalDateTime.now());
		feedback.setUpdateTime(LocalDateTime.now());
		return feedbackMapper.insertSelective(feedback);
	}

	public List<DtsFeedback> querySelective(Integer userId, String username, Integer page, Integer limit, String sort,
			String order) {
		DtsFeedbackExample example = new DtsFeedbackExample();
		DtsFeedbackExample.Criteria criteria = example.createCriteria();

		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}
		if (!StringUtils.isEmpty(username)) {
			criteria.andUsernameLike("%" + username + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return feedbackMapper.selectByExample(example);
	}
}
