package com.haust.service.service.order;

import com.haust.service.domain.order.DtsOrderGoods;

import java.util.List;

public interface DtsOrderGoodsService {
    int add(DtsOrderGoods orderGoods);
    List<DtsOrderGoods> queryByOid(Integer orderId);
    List<DtsOrderGoods> findByOidAndGid(Integer orderId, Integer goodsId);
    DtsOrderGoods findById(Integer id);
    void updateById(DtsOrderGoods orderGoods);
    Short getComments(Integer orderId);
    boolean checkExist(Integer goodsId);
}