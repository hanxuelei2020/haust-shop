package com.haust.service.service.product;

import com.haust.service.domain.product.DtsCategory;

import java.util.List;

public interface DtsCategoryService {
    public List<DtsCategory> queryL1WithoutRecommend(int offset, int limit);
    public List<DtsCategory> queryL1(int offset, int limit);
    public List<DtsCategory> queryL1();
    public List<DtsCategory> queryByPid(Integer pid);
    public List<DtsCategory> queryL2ByIds(List<Integer> ids);
    public DtsCategory findById(Integer id);
    public List<DtsCategory> querySelective(String id, String name, Integer page, Integer size, String sort,
                                            String order);
    public int updateById(DtsCategory category);
    public void deleteById(Integer id);
    public void add(DtsCategory category);
    public List<DtsCategory> queryChannel();
}
