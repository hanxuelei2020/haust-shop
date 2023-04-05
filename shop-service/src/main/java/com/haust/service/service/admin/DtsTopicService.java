package com.haust.service.service.admin;

import com.haust.service.domain.product.DtsTopic;

import java.util.List;

public interface DtsTopicService {

    List<DtsTopic> queryList(int offset, int limit);

    List<DtsTopic> queryList(int offset, int limit, String sort, String order);

    int queryTotal();

    DtsTopic findById(Integer id);

    List<DtsTopic> queryRelatedList(Integer id, int offset, int limit);

    List<DtsTopic> querySelective(String title, String subtitle, Integer page, Integer limit, String sort,
                                  String order);

    int updateById(DtsTopic topic);

    void deleteById(Integer id);

    void add(DtsTopic topic);
}