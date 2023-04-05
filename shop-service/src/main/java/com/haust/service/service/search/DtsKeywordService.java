package com.haust.service.service.search;

import com.haust.service.domain.search.DtsKeyword;

import java.util.List;

public interface DtsKeywordService {
	
	public DtsKeyword queryDefault();
	
	public List<DtsKeyword> queryHots();
	
	public List<DtsKeyword> queryByKeyword(String keyword, Integer page, Integer size);
	
	public List<DtsKeyword> querySelective(String keyword, String url, Integer page, Integer limit, String sort, String order);
	
	public void add(DtsKeyword keywords);
	
	public DtsKeyword findById(Integer id);
	
	public int updateById(DtsKeyword keywords);
	
	public void deleteById(Integer id);
}