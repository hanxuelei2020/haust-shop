package com.haust.service.service.admin;

import com.haust.service.domain.admin.DtsSystem;

import java.util.List;

public interface DtsSystemConfigService {

    /**
     * 查询所有系统配置
     *
     * @return 系统配置列表
     */
    List<DtsSystem> queryAll();
}