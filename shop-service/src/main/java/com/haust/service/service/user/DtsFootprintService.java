package com.haust.service.service.user;

import com.haust.service.domain.user.DtsFootprint;

import java.util.List;

public interface DtsFootprintService {

    List<DtsFootprint> queryByAddTime(Integer userId, Integer page, Integer size);

    DtsFootprint findById(Integer id);

    void deleteById(Integer id);

    void add(DtsFootprint footprint);

    List<DtsFootprint> querySelective(String userId, String goodsId, Integer page, Integer size, String sort,
                                      String order);
}