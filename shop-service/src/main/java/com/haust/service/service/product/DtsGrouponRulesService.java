package com.haust.service.service.product;

import com.haust.service.domain.order.DtsGrouponRules;

import java.util.List;
import java.util.Map;

public interface DtsGrouponRulesService {
    public int createRules(DtsGrouponRules rules);
    public DtsGrouponRules queryById(Integer id);
    public List<DtsGrouponRules> queryByGoodsId(Long goodsId);
    public List<Map<String, Object>> queryList(int offset, int limit);
    public List<Map<String, Object>> queryList(int offset, int limit, String sort, String order);
    public List<DtsGrouponRules> queryGoodRulesList(int offset, int limit, String sort, String order);
    public boolean isExpired(DtsGrouponRules rules);
    public List<DtsGrouponRules> querySelective(String goodsId, Integer page, Integer size, String sort, String order);
    public void delete(Integer id);
    public int updateById(DtsGrouponRules grouponRules);
    public List<DtsGrouponRules> queryBrandGrouponRules(List<Integer> brandIds, String goodsId, Integer page,
                                                        Integer size, String sort, String order);

}
