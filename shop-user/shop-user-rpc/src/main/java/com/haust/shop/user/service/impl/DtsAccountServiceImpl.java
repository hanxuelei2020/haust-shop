package com.haust.shop.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.haust.common.consts.RocketMqConstant;
import com.haust.service.domain.order.SharedUserVo;
import com.haust.service.domain.user.*;
import com.haust.service.service.user.DtsAccountService;
import com.haust.shop.user.dao.AccountMapperEx;
import com.haust.shop.user.dao.DtsAccountTraceMapper;
import com.haust.shop.user.dao.DtsUserAccountMapper;
import com.haust.shop.user.dao.DtsUserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@DubboService
public class DtsAccountServiceImpl implements DtsAccountService {
	private static final Logger logger = LoggerFactory.getLogger(DtsAccountServiceImpl.class);

	public static final long TWO_MONTH_DAYS = 60;//近两个月,60天

	public static final long ONE_WEEK_DAYS = 7;//近一周

	@Autowired
	private DtsUserAccountMapper userAccountMapper;

	@Autowired
	private DtsAccountTraceMapper accountTraceMapper;

	@Autowired
	private AccountMapperEx accountMapperEx;

	@Autowired
	private DtsUserMapper userMapper;

	@Autowired
	private RocketMQTemplate userOrderTemplate;

	public DtsUserAccount findShareUserAccountByUserId(Integer shareUserId) {

		DtsUserAccountExample example = new DtsUserAccountExample();
		example.or().andUserIdEqualTo(shareUserId);
		List<DtsUserAccount> accounts = userAccountMapper.selectByExample(example);
		// Assert.state(accounts.size() < 2, "同一个用户存在两个账户");
		if (accounts.size() == 1) {
			return accounts.get(0);
		} else {
			logger.error("根据代理用户id：{},获取账号信息出错!!!",shareUserId);
			return null;
		}
	}

	public List<Integer> findAllSharedUserId() {
		return accountMapperEx.getShareUserId();
	}

	@Override
	public Integer getShardLevelUserId(Integer shareUserId) {
		return accountMapperEx.getLevelShareUserId(shareUserId);
	}

	private String getRandomNum(Integer num) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public void setSettleMentAccount(Integer sharedUserId, Integer type, BigDecimal toSettleMoney, String startTime, String endTime) throws Exception {

		if (toSettleMoney == null || toSettleMoney.compareTo(new BigDecimal(0)) <= 0) {//如果无佣金
			toSettleMoney = new BigDecimal(0);
		}

		logger.info("代理用户编号： {" + sharedUserId + "},日期：" + startTime + " - " + endTime + ",获取佣金: " + toSettleMoney
				+ "元");
		if (toSettleMoney.compareTo(new BigDecimal(0)) > 0) {
			settleApplyTrace(sharedUserId, startTime, endTime, type, toSettleMoney,null);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void settleApplyTrace(Integer sharedUserId, String startTime,String endTime,Integer type, BigDecimal toSettleMoney,String mobile) {
		
		Integer settlementRate = 5;
		// 获取用户账户信息并更新记录
		DtsUserAccount account = this.findShareUserAccountByUserId(sharedUserId);
		if (account != null && toSettleMoney.compareTo(new BigDecimal("0")) == 0) {// 如果该用户未产生推荐单，则降低结算比例
			settlementRate = account.getSettlementRate() > 8 ? 8 : account.getSettlementRate();
		}
		
		// 更新订单结算状态
		SendStatus sendStatus = userOrderTemplate.syncSend(RocketMqConstant.USER_ORDER,
				new GenericMessage<SharedUserVo>(new SharedUserVo(sharedUserId, startTime, endTime))).getSendStatus();

		if (!Objects.equals(sendStatus,SendStatus.SEND_OK)) {
			// 消息发不出去就抛异常，发的出去无所谓
			throw new RuntimeException("消息传递异常");
		}
		//更新代理用户账号信息
		account.setRemainAmount(account.getRemainAmount().add(toSettleMoney));//剩余结算,尚未结算给用户
		account.setTotalAmount(account.getTotalAmount().add(toSettleMoney));
		account.setModifyTime(LocalDateTime.now());
		account.setSettlementRate(settlementRate);
		userAccountMapper.updateByPrimaryKeySelective(account);
		
		// 新增账户跟踪表，添加结算跟踪记录
		DtsAccountTrace trace = new DtsAccountTrace();
		trace.setAmount(account.getRemainAmount());//当前申请金额，直接将未结算的进行申请
		trace.setTotalAmount(account.getTotalAmount());//已提现总金额
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		String now = df.format(LocalDate.now());
		String traceSn = now + getRandomNum(6);
		trace.setTraceSn(traceSn);
		trace.setAddTime(LocalDateTime.now());
		trace.setType(type);
		trace.setUserId(sharedUserId);
		trace.setStatus((byte) 0);//申请状态
		trace.setMobile(mobile);
		accountTraceMapper.insert(trace);
	}


	public Map<String, Object> getStatistics(Integer sharedUserId, int dayAgo) {
		Map<String, Object> result = new HashMap<String, Object>();
		LocalDateTime startTime = LocalDateTime.now().minusDays(dayAgo);

		DtsUserExample example = new DtsUserExample();
		example.or().andDeletedEqualTo(false).andShareUserIdEqualTo(sharedUserId)
				.andAddTimeGreaterThanOrEqualTo(startTime);
		Long userCnt = (Long) userMapper.countByExample(example);

		result.put("userCnt", userCnt);

		return result;
	}


	public List<DtsAccountTrace> queryAccountTraceList(Integer userId, Integer page, Integer size) {
		DtsAccountTraceExample example = new DtsAccountTraceExample();
		example.setOrderByClause(DtsAccountTrace.Column.addTime.desc());
		DtsAccountTraceExample.Criteria criteria = example.or();
		criteria.andUserIdEqualTo(userId);
		PageHelper.startPage(page, size);
		return accountTraceMapper.selectByExample(example);
	}

	/**
	 * 新增申请提现记录
	 * 
	 * @param userId
	 * @param applyAmt
	 */
	public void addExtractRecord(Integer userId, BigDecimal applyAmt, String mobile, String smsCode,
			BigDecimal hasAmount) {
		DtsAccountTrace record = new DtsAccountTrace();
		record.setAmount(applyAmt);
		record.setMobile(mobile);
		record.setTotalAmount(applyAmt.add(hasAmount));
		record.setSmsCode(smsCode);

		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		String now = df.format(LocalDate.now());
		String traceSn = now + getRandomNum(6);
		record.setTraceSn(traceSn);

		record.setAddTime(LocalDateTime.now());
		record.setType(1);// 申请中..
		record.setUserId(userId);
		accountTraceMapper.insert(record);
	}

	public void add(DtsUserAccount userAccount) {
		userAccount.setCreateTime(LocalDateTime.now());
		userAccount.setModifyTime(LocalDateTime.now());
		userAccountMapper.insert(userAccount);
	}

	/**
	 * 根据账号和状态，查询提现记录
	 * @param userId 
	 * @param types
	 * @return
	 */
	public List<DtsAccountTrace> getAccountTraceList(Integer userId, Byte... types) {
		if(userId == null || types == null || types.length < 1) {
			return null;
		}
		DtsAccountTraceExample example = new DtsAccountTraceExample();
		List<Byte> statusList = new ArrayList<Byte>();
		for (Byte type : types) {
			statusList.add(type);
		}
		example.or().andUserIdEqualTo(userId).andStatusIn(statusList);
		return accountTraceMapper.selectByExample(example);
	}

	public List<DtsAccountTrace> querySelectiveTrace(List<DtsUser> userList, List<Byte> status, Integer page,
			Integer size, String sort, String order) {
		//是否有匹配到的用户,转用户id集合
		List<Integer> userIdArray = null;
		if (userList != null && userList.size() > 0) {
			userIdArray = new ArrayList<Integer>();
			for (DtsUser dtsUser : userList) {
				userIdArray.add(dtsUser.getId().intValue()) ;
			}
		}
		
		DtsAccountTraceExample example = new DtsAccountTraceExample();
		DtsAccountTraceExample.Criteria criteria = example.or();
		
		if (userIdArray != null && userIdArray.size() != 0) {
			criteria.andUserIdIn(userIdArray);
		}
		if (status != null && status.size() != 0) {
			criteria.andStatusIn(status);
		}
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}
		
		PageHelper.startPage(page, size);
		return accountTraceMapper.selectByExample(example);
		
	}


	/**
	 * 为资金账户的安全，建议做线下销账处理，处理后执行该逻辑
	 * 这里只根据记录做状态调整和审批意见记录
	 * @param traceId
	 * @param status
	 * @param traceMsg
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public boolean approveAccountTrace(Integer traceId, Byte status, String traceMsg) {
		
		DtsAccountTrace trace = accountTraceMapper.selectByPrimaryKey(traceId);
		trace.setStatus(status);
		trace.setTraceMsg(traceMsg);
		if (status.intValue() == 1) {//如果是审批通过，需要消除账户中的可提现余额
			DtsUserAccount userAccount = findShareUserAccountByUserId(trace.getUserId());
			if (userAccount != null) {
				userAccount.setRemainAmount(userAccount.getRemainAmount().subtract(trace.getAmount()));
				logger.info("提现审批通过,调整账户可提现余额为{} - {} = {}",userAccount.getRemainAmount(),trace.getAmount(),userAccount.getRemainAmount().subtract(trace.getAmount()));
				if(userAccountMapper.updateByPrimaryKeySelective(userAccount) == 0) {
					return false;
				}
			} else {
				logger.error("审批提现，获取账号出错！请检查对应用户 userId:{} 的账户",trace.getUserId());
				return false;
			}
		}
		if (accountTraceMapper.updateByPrimaryKeySelective(trace) == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据用户userId,结算用户代理商的结算金额</br>
	 * <p>该方法主要提供给 某个用户从普通用户转代理时调用
	 * 在代理审批通过时，将申请代理人的订单结算金额结算给当前申请人归属的前一个代理<br>
	 *  原因：在没成为代理之前，用户归属为前一个代理用户之下，该用户产生的订单佣金归属于前一个代理用户</p>
	 *  <p>产生误差：因结算时间没有考虑退款情况(正常逻辑考虑了延迟时间，此处是实时结算），
	 *  可能造成这几天内如果发生退款，佣金确已结算给上一个代理用户的情况，因为这种情况产生的概率低，且本身
	 *  佣金数额低，此误差暂时忽略，后续通过定时任务去处理这种异常结算的佣金,联系代理协商</p>
	 * @param userId
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public boolean settlementPreviousAgency(Integer userId, BigDecimal toSettleMoney){
		
		// 获取当前用户是否有未结算的订单(约束：已支付，且无退款，在正常流转的订单)，如果存在则结算给用户的代理人，如果不存在，则结束
		if (toSettleMoney == null || toSettleMoney.compareTo(new BigDecimal("0")) == 0) {// 如果该用户未产生订单
			logger.info("用户 userId:{} 不存在未结算的订单,给其代理人结算佣金结束!");
			return true;
		}
		// 获取当前用户的代理
		DtsUser user = userMapper.selectByPrimaryKey(userId);
		Integer sharedUserId = user.getShareUserId();
		// 获取用户账户信息并更新记录
		DtsUserAccount account = this.findShareUserAccountByUserId(sharedUserId);

		// 更新订单结算状态
		SharedUserVo sharedUserVo = new SharedUserVo();
		sharedUserVo.setShardUserId(userId);
		SendStatus sendStatus = userOrderTemplate.syncSend(RocketMqConstant.USER_ORDER,
				new GenericMessage<SharedUserVo>(sharedUserVo)).getSendStatus();

		if (!Objects.equals(sendStatus,SendStatus.SEND_OK)) {
			// 消息发不出去就抛异常，发的出去无所谓
			throw new RuntimeException("消息传递异常");
		}

		// 更新代理用户账号信息
		account.setRemainAmount(account.getRemainAmount().add(toSettleMoney));// 剩余结算,尚未结算给用户
		account.setTotalAmount(account.getTotalAmount().add(toSettleMoney));
		account.setModifyTime(LocalDateTime.now());
		userAccountMapper.updateByPrimaryKeySelective(account);
		
		return true;
	}
	
}
