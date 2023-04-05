package com.haust.service.service.coupon;

import com.haust.service.domain.coupon.DtsCoupon;
import com.haust.service.domain.coupon.DtsCouponExample;

import java.util.List;

public interface DtsCouponService {
    public List<DtsCoupon> queryList(int offset, int limit, String sort, String order);
    public List<DtsCoupon> queryList(DtsCouponExample.Criteria criteria, int offset, int limit, String sort,
                                     String order);
    public int queryTotal();
    public List<DtsCoupon> queryAvailableList(Integer userId, int offset, int limit);
    public List<DtsCoupon> queryList(int offset, int limit);
    public DtsCoupon findById(Integer id);
    public DtsCoupon findByCode(String code);
    public List<DtsCoupon> queryRegister();
    public List<DtsCoupon> querySelective(String name, Short type, Short status, Integer page, Integer limit,
                                          String sort, String order);
    public void add(DtsCoupon coupon);
    public int updateById(DtsCoupon coupon);
    public void deleteById(Integer id);
    public String generateCode();
    public List<DtsCoupon> queryExpired();
}
