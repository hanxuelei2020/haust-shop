package com.haust.service.service.order;

import com.haust.service.domain.order.DtsGroupon;

import java.util.List;

public interface DtsGrouponService {
    public List<DtsGroupon> queryMyGroupon(Integer userId);
    public List<DtsGroupon> queryMyJoinGroupon(Integer userId);
    public DtsGroupon queryByOrderId(Integer orderId);
    public List<DtsGroupon> queryJoinRecord(Integer id);
    public DtsGroupon queryById(Integer id);
    public int countGroupon(Integer grouponId);
    public int updateById(DtsGroupon groupon);
    public int createGroupon(DtsGroupon groupon);
    public List<DtsGroupon> querySelective(String rulesId, Integer page, Integer size, String sort, String order);
    public List<DtsGroupon> queryBrandGroupons(List<Integer> goodIds, String rulesId, Integer page, Integer size,
                                               String sort, String order);


}
