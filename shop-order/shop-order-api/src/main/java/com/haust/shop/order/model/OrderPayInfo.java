package com.haust.shop.order.model;

public class OrderPayInfo {
    private String openId;
    private Integer totalFee;
    private Integer orderSn;
    private String payId;

    public OrderPayInfo() {
    }

    public OrderPayInfo(String openId, Integer totalFee, Integer orderSn, String payId) {
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

    public Integer getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Integer orderSn) {
        this.orderSn = orderSn;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
