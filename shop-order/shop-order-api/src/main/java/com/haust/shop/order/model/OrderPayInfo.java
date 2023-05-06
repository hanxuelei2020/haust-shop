package com.haust.shop.order.model;

public class OrderPayInfo {
    private String openId;
    private Integer totalFee;
    private String orderSn;
    private String payId;

    public OrderPayInfo() {
    }

    public OrderPayInfo(String openId, Integer totalFee, String orderSn, String payId) {
        this.openId = openId;
        this.totalFee = totalFee;
        this.orderSn = orderSn;
        this.payId = payId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
