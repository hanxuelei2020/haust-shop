package com.haust.service.service.admin;


import com.haust.service.domain.admin.DtsPermission;

import java.util.Set;

public interface DtsPermissionService {
    public Set<String> queryByRoleIds(Integer[] roleIds);
    public Set<String> queryByRoleId(Integer roleId);
    public boolean checkSuperPermission(Integer roleId);
    public void deleteByRoleId(Integer roleId);
    public void add(DtsPermission DtsPermission);
}