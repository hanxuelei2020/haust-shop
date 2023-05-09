package com.haust.shop.product.mapper;

import com.haust.common.util.CategorySellAmts;
import com.haust.common.util.DayStatis;
import com.haust.service.domain.order.CloseOrder;
import com.haust.service.domain.product.CategorySellGoodVo;
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
	List<CategorySellGoodVo> categorySellStatis(@Param("closeOrders") List<String> closeOrders);


	/**
	 * 获得所有销售成功的订单
	 * @return
	 */
	List<CloseOrder> closeOrders();

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