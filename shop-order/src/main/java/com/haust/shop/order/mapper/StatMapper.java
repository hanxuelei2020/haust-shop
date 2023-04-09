package com.haust.shop.order.mapper;

import com.haust.common.util.CategorySellAmts;
import com.haust.common.util.DayStatis;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
@SuppressWarnings("rawtypes")
public interface StatMapper {
	
	/**
	 * 统计近多少天之内的用户增长量
	 * @param daysAgo
	 * @return
	 */
	List<DayStatis> statisIncreaseUserCnt(@Param("daysAgo") int daysAgo);
	
	/**
	 * 统计近多少天之内的订单增长量
	 * @param daysAgo
	 * @return
	 */
	List<DayStatis> statisIncreaseOrderCnt(@Param("daysAgo") int daysAgo);

	/**
	 * 类目销售额统计
	 * @return
	 */
	List<CategorySellAmts> categorySellStatis();
	
	/**
	 * 用户数统计
	 * @return
	 */
	@MapKey("day")
	List<Map> statUser();

	/**
	 * 订单数统计
	 * @return
	 */
	@MapKey("day")
	List<Map> statOrder();

	/**
	 * 商品数统计
	 * @return
	 */
	@MapKey("day")
	List<Map> statGoods();

	
}