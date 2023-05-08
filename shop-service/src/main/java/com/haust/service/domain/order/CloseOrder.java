package com.haust.service.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class CloseOrder implements Serializable {
    private String goodsId;
    private BigDecimal price;

    public CloseOrder() {
    }

    public CloseOrder(String goodsId, BigDecimal price) {
        this.goodsId = goodsId;
        this.price = price;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
