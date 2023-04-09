package com.haust.shop.product.service.impl;

import com.haust.service.domain.product.DtsGoodsAttribute;
import com.haust.service.domain.product.DtsGoodsAttributeExample;
import com.haust.service.service.product.DtsGoodsAttributeService;
import com.haust.shop.product.mapper.DtsGoodsAttributeMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsGoodsAttributeServiceImpl implements DtsGoodsAttributeService {
	@Autowired
	private DtsGoodsAttributeMapper goodsAttributeMapper;

	public List<DtsGoodsAttribute> queryByGid(Integer goodsId) {
		DtsGoodsAttributeExample example = new DtsGoodsAttributeExample();
		example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
		return goodsAttributeMapper.selectByExample(example);
	}

	public void add(DtsGoodsAttribute goodsAttribute) {
		goodsAttribute.setAddTime(LocalDateTime.now());
		goodsAttribute.setUpdateTime(LocalDateTime.now());
		goodsAttributeMapper.insertSelective(goodsAttribute);
	}

	public DtsGoodsAttribute findById(Integer id) {
		return goodsAttributeMapper.selectByPrimaryKey(id);
	}

	public void deleteByGid(Integer gid) {
		DtsGoodsAttributeExample example = new DtsGoodsAttributeExample();
		example.or().andGoodsIdEqualTo(gid);
		goodsAttributeMapper.logicalDeleteByExample(example);
	}
}
