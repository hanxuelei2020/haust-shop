package com.haust.shop.order.service.Impl;

import com.haust.service.domain.order.DtsOrderGoods;
import com.haust.service.domain.order.DtsOrderGoodsExample;
import com.haust.service.service.order.DtsOrderGoodsService;
import com.haust.shop.order.mapper.DtsOrderGoodsMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsOrderGoodsServiceImpl implements DtsOrderGoodsService {
	@Autowired
	private DtsOrderGoodsMapper orderGoodsMapper;

	public int add(DtsOrderGoods orderGoods) {
		orderGoods.setAddTime(LocalDateTime.now());
		orderGoods.setUpdateTime(LocalDateTime.now());
		return orderGoodsMapper.insertSelective(orderGoods);
	}

	public List<DtsOrderGoods> queryByOid(Integer orderId) {
		DtsOrderGoodsExample example = new DtsOrderGoodsExample();
		example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
		return orderGoodsMapper.selectByExample(example);
	}

	public List<DtsOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId) {
		DtsOrderGoodsExample example = new DtsOrderGoodsExample();
		example.or().andOrderIdEqualTo(orderId).andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
		return orderGoodsMapper.selectByExample(example);
	}

	public DtsOrderGoods findById(Integer id) {
		return orderGoodsMapper.selectByPrimaryKey(id);
	}

	public void updateById(DtsOrderGoods orderGoods) {
		orderGoods.setUpdateTime(LocalDateTime.now());
		orderGoodsMapper.updateByPrimaryKeySelective(orderGoods);
	}

	public Short getComments(Integer orderId) {
		DtsOrderGoodsExample example = new DtsOrderGoodsExample();
		example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
		long count = orderGoodsMapper.countByExample(example);
		return (short) count;
	}

	public boolean checkExist(Integer goodsId) {
		DtsOrderGoodsExample example = new DtsOrderGoodsExample();
		example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
		return orderGoodsMapper.countByExample(example) != 0;
	}
}
