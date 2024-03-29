package com.haust.shop.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.user.DtsAddress;
import com.haust.service.domain.user.DtsAddressExample;
import com.haust.service.service.user.DtsAddressService;
import com.haust.shop.user.dao.DtsAddressMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsAddressServiceImpl implements DtsAddressService {
	@Autowired
	private DtsAddressMapper addressMapper;

	public List<DtsAddress> queryByUid(Integer uid) {
		DtsAddressExample example = new DtsAddressExample();
		example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
		return addressMapper.selectByExample(example);
	}

	public DtsAddress findById(Integer id) {
		return addressMapper.selectByPrimaryKey(id);
	}

	public int add(DtsAddress address) {
		address.setAddTime(LocalDateTime.now());
		address.setUpdateTime(LocalDateTime.now());
		return addressMapper.insertSelective(address);
	}

	public int update(DtsAddress address) {
		address.setUpdateTime(LocalDateTime.now());
		return addressMapper.updateByPrimaryKeySelective(address);
	}

	public void delete(Integer id) {
		addressMapper.logicalDeleteByPrimaryKey(id);
	}

	public DtsAddress findDefault(Integer userId) {
		DtsAddressExample example = new DtsAddressExample();
		example.or().andUserIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
		return addressMapper.selectOneByExample(example);
	}

	/**
	 * 取消用户的默认地址配置
	 * 
	 * @param userId
	 */
	public void resetDefault(Integer userId) {
		DtsAddress address = new DtsAddress();
		address.setIsDefault(false);
		address.setUpdateTime(LocalDateTime.now());
		DtsAddressExample example = new DtsAddressExample();
		example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false).andIsDefaultEqualTo(true);
		addressMapper.updateByExampleSelective(address, example);
	}

	public List<DtsAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort,
			String order) {
		DtsAddressExample example = new DtsAddressExample();
		DtsAddressExample.Criteria criteria = example.createCriteria();

		if (userId != null) {
			criteria.andUserIdEqualTo(userId);
		}
		if (!StringUtils.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return addressMapper.selectByExample(example);
	}
}
