package com.haust.service.service.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

public interface WxOrderService {
    public Object list(Integer userId, Integer showType, Integer page, Integer size);
    public Object detail(Integer userId, Integer orderId);
    public Object submit(Integer userId, String body);
    public Object cancel(Integer userId, String body);
    public Object prepay(Integer userId, String body, HttpServletRequest request);
    public Object dtsPayNotify(HttpServletRequest request, HttpServletResponse response);
    public Object refund(Integer userId, String body);
    public Object confirm(Integer userId, String body);
    public Object delete(Integer userId, String body);
    public Object goods(Integer userId, Integer orderId, Integer goodsId);
    public Object comment(Integer userId, String body);
    public Object settleOrderList(Integer userId, Integer showType, Integer page, Integer size);
    public Object extractList(Integer userId, Integer page, Integer size);
    public Object expressTrace(Integer userId, @NotNull Integer orderId);
}
