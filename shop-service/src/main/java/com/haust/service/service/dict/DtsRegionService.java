package com.haust.service.service.dict;

import com.haust.service.domain.dict.DtsRegion;

import java.util.List;

public interface DtsRegionService {

    /**
     * 获取所有区域信息
     * 
     * @return 区域列表
     */
    List<DtsRegion> getAll();

    /**
     * 根据父级id获取区域信息
     * 
     * @param parentId 父级id
     * @return 区域列表
     */
    List<DtsRegion> queryByPid(Integer parentId);

    /**
     * 根据id获取区域信息
     * 
     * @param id 区域id
     * @return 区域信息
     */
    DtsRegion findById(Integer id);

    /**
     * 根据条件查询区域信息
     * 
     * @param name  区域名称
     * @param code  区域编码
     * @param page  页码
     * @param size  页面大小
     * @param sort  排序字段
     * @param order 排序方式
     * @return 区域列表
     */
    List<DtsRegion> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order);

}