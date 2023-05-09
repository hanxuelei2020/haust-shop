package com.haust.service.service.order;

import com.haust.common.util.CategorySellAmts;
import com.haust.common.util.DayStatis;
import com.haust.service.domain.order.CloseOrder;
import com.haust.service.domain.order.DtsOrder;

import java.util.List;
import java.util.Map;

public interface DtsOrderService {
    int add(DtsOrder order);

    int count(Integer userId);

    DtsOrder findById(Integer orderId);

    int countByOrderSn(Integer userId, String orderSn);

    String generateOrderSn(Integer userId);

    List<DtsOrder> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer size);

    List<DtsOrder> querySelective(Integer userId, String orderSn, List<Short> orderStatusArray, Integer page,
            Integer size, String sort, String order);

    int updateWithOptimisticLocker(DtsOrder order);

    void deleteById(Integer id);

    int count();

    List<DtsOrder> queryUnpaid();

    List<DtsOrder> queryUnconfirm();

    DtsOrder findBySn(String orderSn);

    Map<Object, Object> orderInfo(Integer userId);
    public List<DtsOrder> queryComment();
    public List<DayStatis> recentCount(int statisDaysRang);
    public List<CloseOrder> categorySell();
    public List<DtsOrder> queryBrandSelective(List<Integer> brandIds, Integer userId, String orderSn,
                                              List<Short> orderStatusArray, Integer page, Integer size, String sort, String order);

    public List<Map> statOrder();

    public List<Map> statGoods();
}