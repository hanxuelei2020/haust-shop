package com.haust.shop.third.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.third.DtsRegion;
import com.haust.service.domain.third.DtsRegionExample;
import com.haust.service.service.third.DtsRegionService;
import com.haust.shop.third.mapper.DtsRegionMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@DubboService
public class DtsRegionServiceImpl implements DtsRegionService {

	@Autowired
	private DtsRegionMapper regionMapper;

	public List<DtsRegion> getAll() {
		DtsRegionExample example = new DtsRegionExample();
		byte b = 4;
		example.or().andTypeNotEqualTo(b);
		return regionMapper.selectByExample(example);
	}

	public List<DtsRegion> queryByPid(Integer parentId) {
		DtsRegionExample example = new DtsRegionExample();
		example.or().andPidEqualTo(parentId);
		return regionMapper.selectByExample(example);
	}

	public DtsRegion findById(Integer id) {
		return regionMapper.selectByPrimaryKey(id);
	}

	public List<DtsRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort,
			String order) {
		DtsRegionExample example = new DtsRegionExample();
		DtsRegionExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (!StringUtils.isEmpty(code)) {
			criteria.andCodeEqualTo(code);
		}

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return regionMapper.selectByExample(example);
	}
}
