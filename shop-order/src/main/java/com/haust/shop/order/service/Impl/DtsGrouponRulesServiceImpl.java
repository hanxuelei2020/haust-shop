package com.haust.shop.order.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.haust.service.domain.order.DtsGrouponRules;
import com.haust.service.domain.order.DtsGrouponRulesExample;
import com.haust.service.domain.product.DtsGoods;
import com.haust.service.service.product.DtsGrouponRulesService;
import com.haust.shop.order.mapper.DtsGrouponRulesMapper;
import com.haust.shop.order.mapper.GrouponMapperEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DtsGrouponRulesServiceImpl implements DtsGrouponRulesService {
	@Autowired
	private DtsGrouponRulesMapper mapper;
	@Autowired
	private GrouponMapperEx grouponMapperEx;
	
	private DtsGoods.Column[] goodsColumns = new DtsGoods.Column[] { DtsGoods.Column.id, DtsGoods.Column.name,
			DtsGoods.Column.brief, DtsGoods.Column.picUrl, DtsGoods.Column.counterPrice, DtsGoods.Column.retailPrice };

	public int createRules(DtsGrouponRules rules) {
		rules.setAddTime(LocalDateTime.now());
		rules.setUpdateTime(LocalDateTime.now());
		return mapper.insertSelective(rules);
	}

	/**
	 * 根据ID查找对应团购项
	 *
	 * @param id
	 * @return
	 */
	public DtsGrouponRules queryById(Integer id) {
		DtsGrouponRulesExample example = new DtsGrouponRulesExample();
		example.or().andIdEqualTo(id).andDeletedEqualTo(false);
		return mapper.selectOneByExample(example);
	}

	/**
	 * 查询某个商品关联的团购规则
	 *
	 * @param goodsId
	 * @return
	 */
	public List<DtsGrouponRules> queryByGoodsId(Long goodsId) {
		DtsGrouponRulesExample example = new DtsGrouponRulesExample();
		example.or().andGoodsIdEqualTo(goodsId).andDeletedEqualTo(false);
		return mapper.selectByExample(example);
	}

	/**
	 * 获取首页团购活动列表
	 *
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Deprecated
	public List<Map<String, Object>> queryList(int offset, int limit) {
		return queryList(offset, limit, "add_time", "desc");
	}

	/**
	 * 由于当前方法嵌入了其他服务的mapper这里需要进行修改
	 * @param offset
	 * @param limit
	 * @param sort
	 * @param order
	 * @return
	 */
	@Deprecated
	public List<Map<String, Object>> queryList(int offset, int limit, String sort, String order) {
		DtsGrouponRulesExample example = new DtsGrouponRulesExample();
		example.or().andDeletedEqualTo(false);
		example.setOrderByClause(sort + " " + order);
		PageHelper.startPage(offset, limit);
		List<DtsGrouponRules> grouponRules = mapper.selectByExample(example);

		List<Map<String, Object>> grouponList = new ArrayList<>(grouponRules.size());
		for (DtsGrouponRules rule : grouponRules) {
			Integer goodsId = rule.getGoodsId().intValue();
			// todo 这里不能调用其他服务的mapper,只能从controller上层去调用用另一个的服务后续处理
//			DtsGoods goods = goodsMapper.selectByPrimaryKeySelective(goodsId, goodsColumns);
//			if (goods == null)
//				continue;
//
//			Map<String, Object> item = new HashMap<>();
//			item.put("goods", goods);
//			item.put("groupon_price", goods.getRetailPrice().subtract(rule.getDiscount()));
//			item.put("groupon_member", rule.getDiscountMember());
//			grouponList.add(item);
		}

		return grouponList;
	}

	/**
	 * 返回 rule
	 * @param offset
	 * @param limit
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<DtsGrouponRules> queryGoodRulesList(int offset, int limit, String sort, String order) {
		DtsGrouponRulesExample example = new DtsGrouponRulesExample();
		example.or().andDeletedEqualTo(false);
		example.setOrderByClause(sort + " " + order);
		PageHelper.startPage(offset, limit);
		return  mapper.selectByExample(example);
	}

	/**
	 * 判断某个团购活动是否已经过期
	 *
	 * @return
	 */
	public boolean isExpired(DtsGrouponRules rules) {
		return (rules == null || rules.getExpireTime().isBefore(LocalDateTime.now()));
	}

	/**
	 * 获取团购活动列表
	 *
	 * @param goodsId
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<DtsGrouponRules> querySelective(String goodsId, Integer page, Integer size, String sort, String order) {
		DtsGrouponRulesExample example = new DtsGrouponRulesExample();
		example.setOrderByClause(sort + " " + order);

		DtsGrouponRulesExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(goodsId)) {
			criteria.andGoodsIdEqualTo(Long.parseLong(goodsId));
		}
		criteria.andDeletedEqualTo(false);

		PageHelper.startPage(page, size);
		return mapper.selectByExample(example);
	}

	public void delete(Integer id) {
		mapper.logicalDeleteByPrimaryKey(id);
	}

	public int updateById(DtsGrouponRules grouponRules) {
		grouponRules.setUpdateTime(LocalDateTime.now());
		return mapper.updateByPrimaryKeySelective(grouponRules);
	}

	/**
	 * 查询品牌入驻管理员团购规则
	 * @param brandIds
	 * @param goodsId
	 * @param page
	 * @param size
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<DtsGrouponRules> queryBrandGrouponRules(List<Integer> brandIds, String goodsId, Integer page,
			Integer size, String sort, String order) {
		String orderBySql = null;
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			orderBySql = "o."+sort + " " + order;
		}
		
		String brandIdsSql = null;
		if (brandIds != null) {
			brandIdsSql = "";
			for (Integer brandId : brandIds) {
				brandIdsSql += "," + brandId;
			}
			brandIdsSql = " and g.brand_id in (" + brandIdsSql.substring(1) + ") ";
		}
		
		PageHelper.startPage(page, size);
		
		return grouponMapperEx.queryBrandGrouponRules(goodsId,orderBySql,brandIdsSql);
	}
}