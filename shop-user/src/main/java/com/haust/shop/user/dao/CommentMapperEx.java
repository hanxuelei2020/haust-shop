package com.haust.shop.user.dao;

import com.haust.service.domain.user.DtsComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论管理DAO层接口
 * @author 韩雪磊
 * @since 1.0.0
 */
@Mapper
public interface CommentMapperEx {

	/**
	 * 按入驻店铺查询归属的评论信息
	 * @param userId
	 * @param valueId
	 * @param orderBySql
	 * @param brandIdsSql
	 * @return
	 */
	List<DtsComment> queryBrandComment(@Param("type") Byte type, @Param("userId") String userId, @Param("valueId") String valueId, @Param("orderBySql") String orderBySql, @Param("brandIdsSql") String brandIdsSql);

}
