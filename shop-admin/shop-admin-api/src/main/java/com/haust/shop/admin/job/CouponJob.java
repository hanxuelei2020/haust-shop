package com.haust.shop.admin.job;

import com.haust.common.consts.CouponConstant;
import com.haust.common.consts.CouponUserConstant;
import com.haust.service.domain.coupon.DtsCoupon;
import com.haust.service.domain.coupon.DtsCouponUser;
import com.haust.service.service.coupon.DtsCouponService;
import com.haust.service.service.coupon.DtsCouponUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 检测优惠券过期情况
 */
@Component
public class CouponJob {
	private final Log logger = LogFactory.getLog(CouponJob.class);

	@DubboReference
	private DtsCouponService couponService;
	@DubboReference
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
