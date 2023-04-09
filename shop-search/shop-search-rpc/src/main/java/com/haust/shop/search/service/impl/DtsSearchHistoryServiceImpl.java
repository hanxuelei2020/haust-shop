package com.haust.shop.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.search.DtsSearchHistory;
import com.haust.service.domain.search.DtsSearchHistoryExample;
import com.haust.service.service.search.DtsSearchHistoryService;
import com.haust.shop.search.mapper.DtsSearchHistoryMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsSearchHistoryServiceImpl implements DtsSearchHistoryService {
	@Autowired
	private DtsSearchHistoryMapper searchHistoryMapper;

	public void save(DtsSearchHistory searchHistory) {
		searchHistory.setAddTime(LocalDateTime.now());
		searchHistory.setUpdateTime(LocalDateTime.now());
		searchHistoryMapper.insertSelective(searchHistory);
	}

	public List<DtsSearchHistory> queryByUid(int uid) {
		DtsSearchHistoryExample example = new DtsSearchHistoryExample();
		example.or().andUserIdEqualTo(uid).andDeletedEqualTo(false);
		example.setDistinct(true);
		return searchHistoryMapper.selectByExampleSelective(example, DtsSearchHistory.Column.keyword);
	}

	public void deleteByUid(int uid) {
		DtsSearchHistoryExample example = new DtsSearchHistoryExample();
		example.or().andUserIdEqualTo(uid);
		searchHistoryMapper.logicalDeleteByExample(example);
	}

	public List<DtsSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort,
			String order) {
		DtsSearchHistoryExample example = new DtsSearchHistoryExample();
		DtsSearchHistoryExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(userId)) {
			criteria.andUserIdEqualTo(Integer.valueOf(userId));
		}
		if (!StringUtils.isEmpty(keyword)) {
			criteria.andKeywordLike("%" + keyword + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return searchHistoryMapper.selectByExample(example);
	}
}
