package com.haust.service.service.order;

import com.haust.service.domain.order.DtsGrouponRules;

import java.util.List;
import java.util.Map;

public interface DtoGrouponRulesService {
    int createRules(DtsGrouponRules rules);

    DtsGrouponRules queryById(Integer id);

    List<DtsGrouponRules> queryByGoodsId(Long goodsId);

    List<Map<String, Object>> queryList(int offset, int limit);

    List<Map<String, Object>> queryList(int offset, int limit, String sort, String order);

    boolean isExpired(DtsGrouponRules rules);

    List<DtsGrouponRules> querySelective(String goodsId, Integer page, Integer size, String sort, String order);
    public void delete(Integer id);
    public int updateById(DtsGrouponRules grouponRules);
    public List<DtsGrouponRules> queryBrandGrouponRules(List<Integer> brandIds, String goodsId, Integer page,
                                                        Integer size, String sort, String order);

}