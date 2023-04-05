package com.haust.service.service.coupon;

import com.haust.service.domain.coupon.DtsCouponUser;

import java.util.List;

public interface DtsCouponUserService {

    Integer countCoupon(Integer couponId);

    Integer countUserAndCoupon(Integer userId, Integer couponId);

    void add(DtsCouponUser couponUser);

    List<DtsCouponUser> queryList(Integer userId, Integer couponId, Short status, Integer page, Integer size, String sort, String order);

    List<DtsCouponUser> queryAll(Integer userId, Integer couponId);

    List<DtsCouponUser> queryAll(Integer userId);

    DtsCouponUser queryOne(Integer userId, Integer couponId);

    DtsCouponUser findById(Integer id);

    int update(DtsCouponUser couponUser);

    List<DtsCouponUser> queryExpired();
}