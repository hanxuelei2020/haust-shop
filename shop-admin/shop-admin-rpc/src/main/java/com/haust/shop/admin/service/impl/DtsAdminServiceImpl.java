package com.haust.shop.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.admin.DtsAdmin;
import com.haust.service.domain.admin.DtsAdminExample;
import com.haust.service.service.admin.DtsAdminService;

import com.haust.shop.admin.mapper.DtsAdminMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsAdminServiceImpl implements DtsAdminService {
	private final DtsAdmin.Column[] result = new DtsAdmin.Column[] { DtsAdmin.Column.id, DtsAdmin.Column.username,
			DtsAdmin.Column.avatar, DtsAdmin.Column.roleIds };
	@Autowired
	private DtsAdminMapper adminMapper;

	public List<DtsAdmin> findAdmin(String username) {
		DtsAdminExample example = new DtsAdminExample();
		example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
		return adminMapper.selectByExample(example);
	}

	public DtsAdmin findAdmin(Integer id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	public List<DtsAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
		DtsAdminExample example = new DtsAdminExample();
		DtsAdminExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(username)) {
			criteria.andUsernameLike("%" + username + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return adminMapper.selectByExampleSelective(example, result);
	}

	public int updateById(DtsAdmin admin) {
		admin.setUpdateTime(LocalDateTime.now());
		return adminMapper.updateByPrimaryKeySelective(admin);
	}

	public void deleteById(Integer id) {
		adminMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(DtsAdmin admin) {
		admin.setAddTime(LocalDateTime.now());
		admin.setUpdateTime(LocalDateTime.now());
		adminMapper.insertSelective(admin);
	}

	public DtsAdmin findById(Integer id) {
		return adminMapper.selectByPrimaryKeySelective(id, result);
	}

	public List<DtsAdmin> allAdmin() {
		DtsAdminExample example = new DtsAdminExample();
		example.or().andDeletedEqualTo(false);
		return adminMapper.selectByExample(example);
	}
}
