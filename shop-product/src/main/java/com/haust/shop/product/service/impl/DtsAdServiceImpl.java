package com.haust.shop.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.product.DtsAd;
import com.haust.service.domain.product.DtsAdExample;
import com.haust.service.service.product.DtsAdService;
import com.haust.shop.product.mapper.DtsAdMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsAdServiceImpl implements DtsAdService {
	@Autowired
	private DtsAdMapper adMapper;

	public List<DtsAd> queryIndex() {
		DtsAdExample example = new DtsAdExample();
		example.or().andPositionEqualTo((byte) 1).andDeletedEqualTo(false).andEnabledEqualTo(true);
		return adMapper.selectByExample(example);
	}

	public List<DtsAd> querySelective(String name, String content, Integer page, Integer limit, String sort,
			String order) {
		DtsAdExample example = new DtsAdExample();
		DtsAdExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (!StringUtils.isEmpty(content)) {
			criteria.andContentLike("%" + content + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return adMapper.selectByExample(example);
	}

	public int updateById(DtsAd ad) {
		ad.setUpdateTime(LocalDateTime.now());
		return adMapper.updateByPrimaryKeySelective(ad);
	}

	public void deleteById(Integer id) {
		adMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(DtsAd ad) {
		ad.setAddTime(LocalDateTime.now());
		ad.setUpdateTime(LocalDateTime.now());
		adMapper.insertSelective(ad);
	}

	public DtsAd findById(Integer id) {
		return adMapper.selectByPrimaryKey(id);
	}
}
