package com.haust.service.service.admin;

import com.haust.service.domain.admin.DtsIssue;

import java.util.List;

public interface DtsIssueService {
    public List<DtsIssue> query();
    public void deleteById(Integer id);
    public void add(DtsIssue issue);
    public List<DtsIssue> querySelective(String question, Integer page, Integer size, String sort, String order);
    public int updateById(DtsIssue issue);
    public DtsIssue findById(Integer id);
}
