package com.haust.shop.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.admin.DtsIssue;
import com.haust.service.domain.admin.DtsIssueExample;
import com.haust.service.service.admin.DtsIssueService;

import com.haust.shop.admin.mapper.DtsIssueMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsIssueServiceImpl implements DtsIssueService {
	@Autowired
	private DtsIssueMapper issueMapper;

	public List<DtsIssue> query() {
		DtsIssueExample example = new DtsIssueExample();
		example.or().andDeletedEqualTo(false);
		return issueMapper.selectByExample(example);
	}

	public void deleteById(Integer id) {
		issueMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(DtsIssue issue) {
		issue.setAddTime(LocalDateTime.now());
		issue.setUpdateTime(LocalDateTime.now());
		issueMapper.insertSelective(issue);
	}

	public List<DtsIssue> querySelective(String question, Integer page, Integer size, String sort, String order) {
		DtsIssueExample example = new DtsIssueExample();
		DtsIssueExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(question)) {
			criteria.andQuestionLike("%" + question + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return issueMapper.selectByExample(example);
	}

	public int updateById(DtsIssue issue) {
		issue.setUpdateTime(LocalDateTime.now());
		return issueMapper.updateByPrimaryKeySelective(issue);
	}

	public DtsIssue findById(Integer id) {
		return issueMapper.selectByPrimaryKey(id);
	}
}
