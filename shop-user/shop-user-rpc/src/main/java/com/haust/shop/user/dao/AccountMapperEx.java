package com.haust.shop.user.dao;

import com.haust.service.domain.order.DtsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户账户统计扩展的dao层
 * 
 * @author 韩雪磊
 * @since 1.0.0
 */
@Mapper
public interface AccountMapperEx {

	List<Integer> getShareUserId();

	/**
	 * 获得等级超过 两级的 shareUserId
	 */
	Integer getLevelShareUserId(@Param("sharedUserId") Integer sharedUserId);
}
