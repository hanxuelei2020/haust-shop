package com.haust.service.service.product;

import com.haust.service.domain.product.DtsAd;

import java.util.List;

public interface DtsAdService {
    public List<DtsAd> queryIndex();
    public List<DtsAd> querySelective(String name, String content, Integer page, Integer limit, String sort,
                                      String order);
    public int updateById(DtsAd ad);
    public void deleteById(Integer id);
    public void add(DtsAd ad);
    public DtsAd findById(Integer id);
}
