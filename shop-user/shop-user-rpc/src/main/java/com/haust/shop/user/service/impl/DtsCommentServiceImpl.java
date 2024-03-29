package com.haust.shop.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.user.DtsComment;
import com.haust.service.domain.user.DtsCommentExample;
import com.haust.service.service.user.DtsCommentService;
import com.haust.shop.user.dao.CommentMapperEx;
import com.haust.shop.user.dao.DtsCommentMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsCommentServiceImpl implements DtsCommentService {
	@Autowired
	private DtsCommentMapper commentMapper;
	
	@Autowired
	private CommentMapperEx commentMapperEx;

	public List<DtsComment> queryGoodsByGid(Integer id, int offset, int limit) {
		DtsCommentExample example = new DtsCommentExample();
		example.setOrderByClause(DtsComment.Column.addTime.desc());
		example.or().andValueIdEqualTo(id).andTypeEqualTo((byte) 0).andDeletedEqualTo(false);
		PageHelper.startPage(offset, limit);
		return commentMapper.selectByExample(example);
	}

	public List<DtsComment> query(Byte type, Integer valueId, Integer showType, Integer offset, Integer limit) {
		DtsCommentExample example = new DtsCommentExample();
		example.setOrderByClause(DtsComment.Column.addTime.desc());
		if (showType == 0) {
			example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
		} else if (showType == 1) {
			example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andHasPictureEqualTo(true)
					.andDeletedEqualTo(false);
		} else {
			throw new RuntimeException("showType不支持");
		}
		PageHelper.startPage(offset, limit);
		return commentMapper.selectByExample(example);
	}

	public int count(Byte type, Integer valueId, Integer showType) {
		DtsCommentExample example = new DtsCommentExample();
		if (showType == 0) {
			example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andDeletedEqualTo(false);
		} else if (showType == 1) {
			example.or().andValueIdEqualTo(valueId).andTypeEqualTo(type).andHasPictureEqualTo(true)
					.andDeletedEqualTo(false);
		} else {
			throw new RuntimeException("showType不支持");
		}
		return (int) commentMapper.countByExample(example);
	}

	public int save(DtsComment comment) {
		comment.setAddTime(LocalDateTime.now());
		comment.setUpdateTime(LocalDateTime.now());
		return commentMapper.insertSelective(comment);
	}

	public List<DtsComment> querySelective(String userId, String valueId, Integer page, Integer size, String sort,
			String order) {
		DtsCommentExample example = new DtsCommentExample();
		DtsCommentExample.Criteria criteria = example.createCriteria();

		// type=2 是订单商品回复，这里过滤
		criteria.andTypeNotEqualTo((byte) 2);

		if (!StringUtils.isEmpty(userId)) {
			criteria.andUserIdEqualTo(Integer.valueOf(userId));
		}
		if (!StringUtils.isEmpty(valueId)) {
			criteria.andValueIdEqualTo(Integer.valueOf(valueId)).andTypeEqualTo((byte) 0);
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return commentMapper.selectByExample(example);
	}

	public void deleteById(Integer id) {
		commentMapper.logicalDeleteByPrimaryKey(id);
	}

	public String queryReply(Integer id) {
		DtsCommentExample example = new DtsCommentExample();
		example.or().andTypeEqualTo((byte) 2).andValueIdEqualTo(id);
		List<DtsComment> commentReply = commentMapper.selectByExampleSelective(example, DtsComment.Column.content);
		// 目前业务只支持回复一次
		if (commentReply.size() == 1) {
			return commentReply.get(0).getContent();
		}
		return null;
	}

	public DtsComment findById(Integer id) {
		return commentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 入驻店铺对应商品的评价
	 * @param brandIds
	 * @param userId
	 * @param valueId
	 * @param page
	 * @param limit
	 * @param sort
	 * @param order
	 * @return
	 */
	public List<DtsComment> queryBrandCommentSelective(List<Integer> goodIds, String userId, String valueId,
			Integer page, Integer size, String sort, String order) {
		
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
		
		Byte type = (byte) 0;//品牌入驻管理员限定只查商品评论
		return commentMapperEx.queryBrandComment(type,userId,valueId,orderBySql,goodIdsSql);
	}
}
