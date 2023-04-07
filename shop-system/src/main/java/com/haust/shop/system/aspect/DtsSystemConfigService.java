package com.haust.shop.system.aspect;

import com.haust.shop.system.mapper.DtsSystemMapper;
import com.haust.shop.system.model.DtsSystem;
import com.haust.shop.system.model.DtsSystemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DtsSystemConfigService {
	@Autowired
	private DtsSystemMapper systemMapper;

	public List<DtsSystem> queryAll() {
		DtsSystemExample example = new DtsSystemExample();
		example.or();
		return systemMapper.selectByExample(example);
	}
}
