package com.haust.shop.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.haust.common.consts.CommConsts;
import com.haust.common.type.BrokerageTypeEnum;
import com.haust.common.type.WxResponseCode;
import com.haust.common.util.DateTimeUtil;
import com.haust.common.util.JacksonUtil;
import com.haust.common.util.ResponseUtil;
import com.haust.common.util.WxResponseUtil;
import com.haust.common.validator.LoginUser;
import com.haust.service.domain.user.DtsAccountTrace;
import com.haust.service.domain.user.DtsUserAccount;
import com.haust.service.service.order.SettlementOrderService;
import com.haust.service.service.order.WxOrderService;
import com.haust.service.service.user.DtsAccountService;
import com.haust.shop.user.service.impl.DtsAccountServiceImpl;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 佣金业务接口
 * 
 * @since 1.0.0
 * @author 韩雪磊
 */
@RestController
@RequestMapping("/wx/brokerage")
@ShenyuSpringMvcClient("/wx/brokerage/**")
@Validated
public class WxBrokerageController {
	private static final Logger logger = LoggerFactory.getLogger(WxBrokerageController.class);

	@Autowired
	private DtsAccountService accountService;

	@DubboReference
	private WxOrderService wxOrderService;

	@DubboReference
	private SettlementOrderService settlementOrderService;

	/**
	 * 用户结算页面数据
	 * <p>
	 * 目前是用户结算统计信息
	 *
	 * @param userId
	 *            用户ID
	 * @return 用户个人页面数据
	 */
	@GetMapping("main")
	public Object main(@LoginUser Integer userId) {
		logger.info("【请求开始】获取用户结算页面数据,请求参数,userId:{}", userId);
		if (userId == null) {
			logger.error("获取结算信息数据失败:用户未登录！！！");
			return ResponseUtil.unlogin();
		}
		Map<Object, Object> data = new HashMap<Object, Object>();

		// 查询用户账号
		DtsUserAccount userAccount = accountService.findShareUserAccountByUserId(userId);
		BigDecimal totalAmount = new BigDecimal(0.00);
		BigDecimal remainAmount = new BigDecimal(0.00);
		if (userAccount != null) {
			totalAmount = userAccount.getTotalAmount();
			remainAmount = userAccount.getRemainAmount();
		}
		
		//可提现金额 = 已结算未提现 remainAmount + 未结算 unSettleAmount
		BigDecimal unSettleAmount = settlementOrderService.getUnSettleAmount(userId);
		data.put("totalAmount", totalAmount);
		data.put("remainAmount", remainAmount.add(unSettleAmount));

		// 统计数据信息 本月和上月的结算
		String lastMonthEndTime = DateTimeUtil.getPrevMonthEndDay() + " 23:59:59";
		String lastMonthStartTime = DateTimeUtil.getPrevMonthEndDay().substring(0, 7) + "-01 00:00:00";
		Integer shardLevelUserId = accountService.getShardLevelUserId(userId);
		BigDecimal lastMonthSettleMoney = settlementOrderService.getMonthSettleMoney(shardLevelUserId, lastMonthStartTime,
				lastMonthEndTime);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		String currMonthEndTime = today + " 23:59:59";
		String currMonthStartTime = today.substring(0, 7) + "-01 00:00:00";
		BigDecimal currMonthSettleMoney = settlementOrderService.getMonthSettleMoney(shardLevelUserId, currMonthStartTime,
				currMonthEndTime);
		data.put("lastMonthSettleMoney", lastMonthSettleMoney);
		data.put("currMonthSettleMoney", currMonthSettleMoney);

		Map<String, Object> todayData = accountService.getStatistics(userId, 1);
		Map<String, Object> todayDataSettle = settlementOrderService.getStatistics(userId, 1);
		todayData.putAll(todayDataSettle);

		Map<String, Object> yesterdayData = accountService.getStatistics(userId, 2);
		Map<String, Object> yesterdayDataSettle = settlementOrderService.getStatistics(userId, 2);
		yesterdayData.putAll(yesterdayDataSettle);

		Map<String, Object> weekData = accountService.getStatistics(userId, 7);
		Map<String, Object> weekDataSettle = settlementOrderService.getStatistics(userId, 7);
		weekData.putAll(weekDataSettle);

		Map<String, Object> monthData = accountService.getStatistics(userId, 30);
		Map<String, Object> monthDataSettle = settlementOrderService.getStatistics(userId, 30);
		monthData.putAll(monthDataSettle);


		data.put("todayData", todayData);
		data.put("yestodayData", yesterdayData);
		data.put("weekData", weekData);
		data.put("monthData", monthData);

		logger.info("【请求结束】获取用户结算页面数据,响应结果：{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	/**
	 * 推广订单列表
	 *
	 * @param userId
	 *            用户代理用户ID
	 * @param showType
	 *            订单信息： 0，全部订单； 1，有效订单； 2，失效订单； 3，结算订单； 4，待结算订单。
	 * @param page
	 *            分页页数
	 * @param size
	 *            分页大小
	 * @return 推广订单列表
	 */
	@GetMapping("settleOrderList")
	public Object settleOrderList(@LoginUser Integer userId, @RequestParam(defaultValue = "0") Integer showType,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		return wxOrderService.settleOrderList(userId, showType, page, size);
	}

	@GetMapping("extractList")
	public Object extractList(@LoginUser Integer userId, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return wxOrderService.extractList(userId, page, size);
	}

	/**
	 * 提现申请
	 * @param userId
	 * @param body
	 * @return
	 */
	@PostMapping("applyWithdrawal")
	public Object applyWithdrawal(@LoginUser Integer userId, @RequestBody String body) {
		logger.info("【请求开始】提现申请,请求参数,body:{}", body);
		if (userId == null) {
			logger.error("提现申请失败:用户未登录！！！");
			return ResponseUtil.unlogin();
		}
		
		String mobile = JacksonUtil.parseString(body, "mobile");
		//String code = JacksonUtil.parseString(body, "code");
		String amt = JacksonUtil.parseString(body, "amt");

		if (StringUtils.isEmpty(amt) || StringUtils.isEmpty(mobile)) {
			logger.error("提现申请失败:{}", CommConsts.MISS_PARAMS);
			return ResponseUtil.badArgument();
		}

		// 判断验证码是否正确
		/*String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
		if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
			logger.error("提现申请失败:{}", AUTH_CAPTCHA_UNMATCH.desc());
			return WxResponseUtil.fail(AUTH_CAPTCHA_UNMATCH);
		}*/
		
		//验证是否存在未审批通过的申请单，需完成上一个申请才能继续申请下一个提现
		List<DtsAccountTrace> traceList = accountService.getAccountTraceList(userId,(byte)0);
		if (traceList.size() > 0) {
			logger.error("提现申请失败:{}", WxResponseCode.APPLY_WITHDRAWAL_EXIST.desc());
			return WxResponseUtil.fail(WxResponseCode.APPLY_WITHDRAWAL_EXIST);
		}

		LocalDateTime startTime = LocalDateTime.now().minusDays(DtsAccountServiceImpl.TWO_MONTH_DAYS);
		LocalDateTime endTime = LocalDateTime.now().minusDays(DtsAccountServiceImpl.ONE_WEEK_DAYS);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//获取未结算的金额
		BigDecimal unSettleAmount = settlementOrderService.getUnSettleAmount(userId,startTime.format(df),endTime.format(df));
		if (unSettleAmount != null && unSettleAmount.compareTo(new BigDecimal(0)) > 0) {
			accountService.settleApplyTrace(userId, startTime.format(df),endTime.format(df), BrokerageTypeEnum.USER_APPLY.getType().intValue(), unSettleAmount,mobile);
		}

		logger.info("【请求结束】提现申请成功！");
		return ResponseUtil.ok();
	}
}
