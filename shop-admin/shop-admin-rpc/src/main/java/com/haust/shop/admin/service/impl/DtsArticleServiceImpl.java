package com.haust.shop.admin.service.impl;

import java.time.LocalDateTime;
import java.util.List;


import com.haust.service.domain.admin.DtsArticle;
import com.haust.service.domain.admin.DtsArticleExample;
import com.haust.service.service.admin.DtsArticleService;
import com.haust.shop.admin.mapper.DtsArticleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;

@Service
@DubboService
public class DtsArticleServiceImpl implements DtsArticleService {

	@Autowired
	private DtsArticleMapper articleMapper;

	private DtsArticle.Column[] columns = new DtsArticle.Column[] { DtsArticle.Column.id, DtsArticle.Column.title, DtsArticle.Column.addTime, DtsArticle.Column.type };

	public DtsArticle findById(Integer id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	public List<DtsArticle> queryList(int offset, int limit, String sort, String order) {
		DtsArticleExample example = new DtsArticleExample();
		example.or().andDeletedEqualTo(false);
		example.setOrderByClause(sort + " " + order);
		PageHelper.startPage(offset, limit);
		return articleMapper.selectByExampleSelective(example, columns);
	}
	
	public boolean checkExistByTitle(String title) {
		DtsArticleExample example = new DtsArticleExample();
		example.or().andTitleEqualTo(title).andDeletedEqualTo(false);
		return articleMapper.countByExample(example) != 0;
	}

	public void add(DtsArticle article) {
		article.setAddTime(LocalDateTime.now());
		article.setUpdateTime(LocalDateTime.now());
		articleMapper.insertSelective(article);
	}

	public List<DtsArticle> querySelective(String title, Integer page, Integer size, String sort, String order) {
		DtsArticleExample example = new DtsArticleExample();
		DtsArticleExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(title)) {
			criteria.andTitleLike("%" + title + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return articleMapper.selectByExampleWithBLOBs(example);
	}

	public int updateById(DtsArticle article) {
		article.setUpdateTime(LocalDateTime.now());
		return articleMapper.updateByPrimaryKeySelective(article);
	}

	public void deleteById(Integer id) {
		articleMapper.logicalDeleteByPrimaryKey(id);
	}
}
