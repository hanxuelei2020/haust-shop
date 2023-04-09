package com.haust.shop.admin.service;

import java.util.List;

public interface AdminOrderService {
    public Object list(Integer userId, String orderSn, List<Short> orderStatusArray, Integer page, Integer limit,
                       String sort, String order);
    public Object detail(Integer id);
    public Object refund(String body);
    public Object ship(String body);
    public Object reply(String body);
    public Object listShipChannel();

}
