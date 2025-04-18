package com.haust.shop.admin.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.haust.shop.db.domain.DtsCoupon;
import com.haust.shop.db.domain.DtsCouponUser;
import com.haust.shop.db.service.DtsCouponService;
import com.haust.shop.db.service.DtsCouponUserService;
import com.haust.shop.db.util.CouponConstant;
import com.haust.shop.db.util.CouponUserConstant;

/**
 * 检测优惠券过期情况
 */
@Component
public class CouponJob {
	private final Log logger = LogFactory.getLog(CouponJob.class);

	@Autowired
	private DtsCouponService couponService;
	@Autowired
	private DtsCouponUserService couponUserService;

	/**
	 * 每隔一个小时检查
	 */
	@Scheduled(fixedDelay = 60 * 60 * 1000)
	public void checkCouponExpired() {
		logger.info("系统开启任务检查优惠券是否已经过期");

		List<DtsCoupon> couponList = couponService.queryExpired();
		for (DtsCoupon coupon : couponList) {
			coupon.setStatus(CouponConstant.STATUS_EXPIRED);
			couponService.updateById(coupon);
		}

		List<DtsCouponUser> couponUserList = couponUserService.queryExpired();
		for (DtsCouponUser couponUser : couponUserList) {
			couponUser.setStatus(CouponUserConstant.STATUS_EXPIRED);
			couponUserService.update(couponUser);
		}
	}

}
