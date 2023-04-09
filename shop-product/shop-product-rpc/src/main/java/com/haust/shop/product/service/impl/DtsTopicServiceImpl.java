package com.haust.shop.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.product.DtsTopic;
import com.haust.service.domain.product.DtsTopicExample;
import com.haust.service.service.admin.DtsTopicService;
import com.haust.shop.product.mapper.DtsTopicMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@DubboService
public class DtsTopicServiceImpl implements DtsTopicService {
	@Autowired
	private DtsTopicMapper topicMapper;
	private DtsTopic.Column[] columns = new DtsTopic.Column[] { DtsTopic.Column.id, DtsTopic.Column.title,
			DtsTopic.Column.subtitle, DtsTopic.Column.price, DtsTopic.Column.picUrl,
			DtsTopic.Column.readCount };

	public List<DtsTopic> queryList(int offset, int limit) {
		return queryList(offset, limit, "add_time", "desc");
	}

	public List<DtsTopic> queryList(int offset, int limit, String sort, String order) {
		DtsTopicExample example = new DtsTopicExample();
		example.or().andDeletedEqualTo(false);
		example.setOrderByClause(sort + " " + order);
		PageHelper.startPage(offset, limit);
		return topicMapper.selectByExampleSelective(example, columns);
	}

	public int queryTotal() {
		DtsTopicExample example = new DtsTopicExample();
		example.or().andDeletedEqualTo(false);
		return (int) topicMapper.countByExample(example);
	}

	public DtsTopic findById(Integer id) {
		DtsTopicExample example = new DtsTopicExample();
		example.or().andIdEqualTo(id).andDeletedEqualTo(false);
		return topicMapper.selectOneByExampleWithBLOBs(example);
	}

	public List<DtsTopic> queryRelatedList(Integer id, int offset, int limit) {
		DtsTopicExample example = new DtsTopicExample();
		example.or().andIdEqualTo(id).andDeletedEqualTo(false);
		List<DtsTopic> topics = topicMapper.selectByExample(example);
		if (topics.size() == 0) {
			return queryList(offset, limit, "add_time", "desc");
		}
		DtsTopic topic = topics.get(0);

		example = new DtsTopicExample();
		example.or().andIdNotEqualTo(topic.getId()).andDeletedEqualTo(false);
		PageHelper.startPage(offset, limit);
		List<DtsTopic> relateds = topicMapper.selectByExampleWithBLOBs(example);
		if (relateds.size() != 0) {
			return relateds;
		}

		return queryList(offset, limit, "add_time", "desc");
	}

	public List<DtsTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort,
			String order) {
		DtsTopicExample example = new DtsTopicExample();
		DtsTopicExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(title)) {
			criteria.andTitleLike("%" + title + "%");
		}
		if (!StringUtils.isEmpty(subtitle)) {
			criteria.andSubtitleLike("%" + subtitle + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return topicMapper.selectByExampleWithBLOBs(example);
	}

	public int updateById(DtsTopic topic) {
		topic.setUpdateTime(LocalDateTime.now());
		DtsTopicExample example = new DtsTopicExample();
		example.or().andIdEqualTo(topic.getId());
		return topicMapper.updateByExampleSelective(topic, example);
	}

	public void deleteById(Integer id) {
		topicMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(DtsTopic topic) {
		topic.setAddTime(LocalDateTime.now());
		topic.setUpdateTime(LocalDateTime.now());
		topicMapper.insertSelective(topic);
	}

}
