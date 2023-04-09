package com.haust.shop.admin.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.haust.service.domain.admin.DtsRole;
import com.haust.service.domain.admin.DtsRoleExample;
import com.haust.service.service.admin.DtsRoleService;
import com.haust.shop.admin.mapper.DtsRoleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@DubboService
public class DtsRoleServiceImpl implements DtsRoleService {
	@Autowired
	private DtsRoleMapper roleMapper;

	public Set<String> queryByIds(Integer[] roleIds) {
		Set<String> roles = new HashSet<String>();
		if (roleIds.length == 0) {
			return roles;
		}

		DtsRoleExample example = new DtsRoleExample();
		example.or().andIdIn(Arrays.asList(roleIds)).andEnabledEqualTo(true).andDeletedEqualTo(false);
		List<DtsRole> roleList = roleMapper.selectByExample(example);

		for (DtsRole role : roleList) {
			roles.add(role.getName());
		}

		return roles;

	}

	public List<DtsRole> querySelective(String roleName, Integer page, Integer size, String sort, String order) {
		DtsRoleExample example = new DtsRoleExample();
		DtsRoleExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(roleName)) {
			criteria.andNameEqualTo("%" + roleName + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return roleMapper.selectByExample(example);
	}

	public DtsRole findById(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	public void add(DtsRole role) {
		role.setAddTime(LocalDateTime.now());
		role.setUpdateTime(LocalDateTime.now());
		roleMapper.insertSelective(role);
	}

	public void deleteById(Integer id) {
		roleMapper.logicalDeleteByPrimaryKey(id);
	}

	public void updateById(DtsRole role) {
		role.setUpdateTime(LocalDateTime.now());
		roleMapper.updateByPrimaryKeySelective(role);
	}

	public boolean checkExist(String name) {
		DtsRoleExample example = new DtsRoleExample();
		example.or().andNameEqualTo(name).andDeletedEqualTo(false);
		return roleMapper.countByExample(example) != 0;
	}

	public List<DtsRole> queryAll() {
		DtsRoleExample example = new DtsRoleExample();
		example.or().andDeletedEqualTo(false);
		return roleMapper.selectByExample(example);
	}
}
