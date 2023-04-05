package com.haust.service.service.admin;

import com.haust.service.domain.admin.DtsAdmin;

import java.util.List;

public interface DtsAdminService {
    public List<DtsAdmin> findAdmin(String username);
    public DtsAdmin findAdmin(Integer id);
    public List<DtsAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order);
    public int updateById(DtsAdmin admin);
    public void deleteById(Integer id);
    public void add(DtsAdmin admin);
    public DtsAdmin findById(Integer id);
    public List<DtsAdmin> allAdmin();
}
