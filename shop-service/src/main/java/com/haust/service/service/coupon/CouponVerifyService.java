package com.haust.service.service.coupon;

import com.haust.service.domain.coupon.DtsCoupon;

import java.math.BigDecimal;

/**
 * 优惠券验证
 */
public interface CouponVerifyService {
    public DtsCoupon checkCoupon(Integer userId, Integer couponId, BigDecimal checkedGoodsPrice);
}
