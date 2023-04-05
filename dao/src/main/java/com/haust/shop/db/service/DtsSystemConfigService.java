package com.haust.shop.db.service;

import org.springframework.stereotype.Service;

import com.haust.shop.db.dao.DtsSystemMapper;
import com.haust.shop.db.domain.DtsSystem;
import com.haust.shop.db.domain.DtsSystemExample;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DtsSystemConfigService {
	@Resource
	private DtsSystemMapper systemMapper;

	public List<DtsSystem> queryAll() {
		DtsSystemExample example = new DtsSystemExample();
		example.or();
		return systemMapper.selectByExample(example);
	}
}
