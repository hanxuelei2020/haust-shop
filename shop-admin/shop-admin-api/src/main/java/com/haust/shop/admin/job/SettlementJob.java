package com.haust.shop.admin.job;

import com.haust.common.type.BrokerageTypeEnum;
import com.haust.common.util.DateTimeUtil;
import com.haust.service.service.order.SettlementOrderService;
import com.haust.service.service.user.DtsAccountService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代理结算job
 */
@Component
public class SettlementJob {

	private final Log logger = LogFactory.getLog(SettlementJob.class);

	@DubboReference
	private DtsAccountService accountService;

	@DubboReference
	private SettlementOrderService settlementOrderService;

	/**
	 * 自动结算代理佣金
	 * <p>
	 * 每月8-20号 凌晨1点半执行，自动计算代理用户的佣金到对应账户，作为系统结算。
	 * <p>
	 * 注意，仅统计上个月订单状态为已经完成，即订单状态为 401 402 的订单。
	 */
	@Scheduled(cron = "0 30 1 8-20 * ?")
	public void checkOrderUnconfirm() {
		List<Integer> sharedUserIds = accountService.findAllSharedUserId();
		logger.info("自动结算代理佣金定时任务,共找到  " + sharedUserIds.size() + " 位代理用户,开始统计佣金...");

		for (Integer sharedUserId : sharedUserIds) {
			try {
				String prevMonthEndDay = DateTimeUtil.getPrevMonthEndDay();
				// 1.获取用户的代理订单代理金额
				String endTime = prevMonthEndDay + " 23:59:59";
				String startTime = prevMonthEndDay.substring(0, 7) + "-01 00:00:00";
				BigDecimal decimal = settlementOrderService.getUnSettleAmount(sharedUserId, startTime, endTime);
				accountService.setSettleMentAccount(sharedUserId, BrokerageTypeEnum.SYS_APPLY.getType().intValue(), decimal, startTime, endTime);

			} catch (Exception e) {
				logger.error("自动结算出错:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
