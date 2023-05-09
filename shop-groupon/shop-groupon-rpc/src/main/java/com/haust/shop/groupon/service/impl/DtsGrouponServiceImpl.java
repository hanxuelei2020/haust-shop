package com.haust.shop.groupon.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.haust.service.domain.order.DtsGroupon;
import com.haust.service.domain.order.DtsGrouponExample;
import com.haust.service.service.order.DtsGrouponService;
import com.haust.shop.groupon.mapper.DtsGrouponMapper;
import com.haust.shop.groupon.mapper.GrouponMapperEx;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsGrouponServiceImpl implements DtsGrouponService {
	@Autowired
	private DtsGrouponMapper mapper;
	@Autowired
	private GrouponMapperEx grouponMapperEx;

	/**
	 * 获取用户发起的团购记录
	 *
	 * @param userId
	 * @return
	 */
	public List<DtsGroupon> queryMyGroupon(Integer userId) {
		DtsGrouponExample example = new DtsGrouponExample();
		example.or().andUserIdEqualTo(userId).andCreatorUserIdEqualTo(userId).andGrouponIdEqualTo(0)
				.andDeletedEqualTo(false).andPayedEqualTo(true);
		example.orderBy("add_time desc");
		return mapper.selectByExample(example);
	}

	/**
	 * 获取用户参与的团购记录
	 *
	 * @param userId
	 * @return
	 */
	public List<DtsGroupon> queryMyJoinGroupon(Integer userId) {
		DtsGrouponExample example = new DtsGrouponExample();
		example.or().andUserIdEqualTo(userId).andGrouponIdNotEqualTo(0).andDeletedEqualTo(false).andPayedEqualTo(true);
		example.orderBy("add_time desc");
		return mapper.selectByExample(example);
	}

	/**
	 * 根据OrderId查询团购记录
	 *
	 * @param orderId
	 * @return
	 */
	public DtsGroupon queryByOrderId(Integer orderId) {
		DtsGrouponExample example = new DtsGrouponExample();
		example.or().andOrderIdEqualTo(orderId).andDeletedEqualTo(false);
		return mapper.selectOneByExample(example);
	}

	/**
	 * 获取某个团购活动参与的记录
	 *
	 * @param id
	 * @return
	 */
	public List<DtsGroupon> queryJoinRecord(Integer id) {
		DtsGrouponExample example = new DtsGrouponExample();
		example.or().andGrouponIdEqualTo(id).andDeletedEqualTo(false).andPayedEqualTo(true);
		example.orderBy("add_time desc");
		return mapper.selectByExample(example);
	}

	/**
	 * 根据ID查询记录
	 *
	 * @param id
	 * @return
	 */
	public DtsGroupon queryById(Integer id) {
		DtsGrouponExample example = new DtsGrouponExample();
		example.or().andIdEqualTo(id).andDeletedEqualTo(false).andPayedEqualTo(true);
		return mapper.selectOneByExample(example);
	}

	/**
	 * 返回某个发起的团购参与人数
	 *
	 * @param grouponId
	 * @return
	 */
	public int countGroupon(Integer grouponId) {
		DtsGrouponExample example = new DtsGrouponExample();
		example.or().andGrouponIdEqualTo(grouponId).andDeletedEqualTo(false).andPayedEqualTo(true);
		return (int) mapper.countByExample(example);
	}

	public int updateById(DtsGroupon groupon) {
		groupon.setUpdateTime(LocalDateTime.now());
		return mapper.updateByPrimaryKeySelective(groupon);
	}

	/**
	 * 创建或参与一个团购
	 *
	 * @param groupon
	 * @return
	 */
	public int createGroupon(DtsGroupon groupon) {
		groupon.setAddTime(LocalDateTime.now());
		groupon.setUpdateTime(LocalDateTime.now());
		return mapper.insertSelective(groupon);
	}

	/**
	 * 查询所有发起的团购记录
	 *
	 * @param rulesId
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<DtsGroupon> querySelective(String rulesId, Integer page, Integer size, String sort, String order) {
		DtsGrouponExample example = new DtsGrouponExample();
		DtsGrouponExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(rulesId)) {
			criteria.andRulesIdEqualTo(Integer.parseInt(rulesId));
		}
		criteria.andDeletedEqualTo(false);
		criteria.andPayedEqualTo(true);
		criteria.andGrouponIdEqualTo(0);

		PageHelper.startPage(page, size);
		return mapper.selectByExample(example);
	}

	/**
	 * 根据品牌入驻店铺获取对应的团购数据
	 * @param brandIds
	 * @param rulesId
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<DtsGroupon> queryBrandGroupons(List<Integer> goodIds, String rulesId, Integer page, Integer size,
			String sort, String order) {
		String orderBySql = null;
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			orderBySql = "o."+sort + " " + order;
		}

		String goodIdsSql = null;
		if (goodIds != null && !goodIds.isEmpty()) { // 检查列表是否为空
			StringBuilder sb = new StringBuilder();
			for (Integer goodId : goodIds) {
				sb.append(",").append(goodId); // 先追加逗号
			}
			goodIdsSql = " and r.goods_id in (" + sb.substring(1) + ") ";
		}
		
		PageHelper.startPage(page, size);
		
		return grouponMapperEx.queryBrandGroupons(rulesId,orderBySql,goodIdsSql);
	}
}
