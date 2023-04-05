package com.haust.service.service.admin;

import com.haust.service.domain.admin.DtsRole;

import java.util.List;
import java.util.Set;

public interface DtsRoleService {
    public Set<String> queryByIds(Integer[] roleIds);
    public List<DtsRole> querySelective(String roleName, Integer page, Integer size, String sort, String order);
    public DtsRole findById(Integer id);
    public void deleteById(Integer id);
    public void updateById(DtsRole role);
    public boolean checkExist(String name);
    public List<DtsRole> queryAll();
}
