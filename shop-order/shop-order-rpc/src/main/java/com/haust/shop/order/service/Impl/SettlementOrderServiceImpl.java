package com.haust.shop.order.service.Impl;

import com.github.pagehelper.PageHelper;
import com.haust.service.domain.order.DtsOrder;
import com.haust.service.domain.order.SharedUserVo;
import com.haust.service.service.order.SettlementOrderService;
import com.haust.shop.order.mapper.SettlementOrderMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DubboService
public class SettlementOrderServiceImpl implements SettlementOrderService {

    private static final Logger logger = LoggerFactory.getLogger(SettlementOrderServiceImpl.class);

    public static final long TWO_MONTH_DAYS = 60;//近两个月,60天

    public static final long ONE_WEEK_DAYS = 7;//近一周

    @Autowired
    private SettlementOrderMapper settlementOrderMapper;


    @Override
    public BigDecimal getMonthSettleMoney(Integer sharedUserId, String startTime, String endTime) {
        BigDecimal staticSettleMoney = settlementOrderMapper.staticMonthSettleMoney(sharedUserId, startTime, endTime);
        if (staticSettleMoney == null || staticSettleMoney.compareTo(new BigDecimal("0")) == 0) {// 如果该用户未产生推荐单，则降低结算比例
            staticSettleMoney = new BigDecimal(0.00);
        }
        return staticSettleMoney;
    }

    @Override
    public List<DtsOrder> querySettlementOrder(Integer sharedUserId, List<Short> orderStatus,
                                               List<Short> settlementStatus, Integer page, Integer size) {

        String conditionSql = null;
        if (orderStatus != null) {
            conditionSql = "";
            for (Short orderStatu : orderStatus) {
                conditionSql += "," + orderStatu;
            }
            conditionSql = "and o.order_status in (" + conditionSql.substring(1) + ") ";
        }
        if (settlementStatus != null && settlementStatus.size() == 1) {
            conditionSql = conditionSql + " and o.settlement_status =" + settlementStatus.get(0) + " ";
        }

        PageHelper.startPage(page, size);
        return settlementOrderMapper.querySettlementOrder(sharedUserId, conditionSql);
    }

    public BigDecimal getUnSettleAmount(Integer userId) {
        LocalDateTime startTime = LocalDateTime.now().minusDays(TWO_MONTH_DAYS);
        LocalDateTime endTime = LocalDateTime.now().minusDays(ONE_WEEK_DAYS);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getUnSettleAmount(userId,startTime.format(df),endTime.format(df));
    }

    public BigDecimal getUnSettleAmount(Integer userId,String startDay,String endDay) {
        BigDecimal staticSettleMoney = settlementOrderMapper.getToSettleMoney(userId,startDay,endDay);
        if (staticSettleMoney == null || staticSettleMoney.compareTo(new BigDecimal("0")) == 0) {// 如果该用户未产生推荐单，则降低结算比例
            staticSettleMoney = new BigDecimal(0.00);
        }
        logger.info("获取开始时间：{} - 结束时间 ：{} 内 用户id:{} 的未结算佣金 :{}",startDay,endDay,userId,staticSettleMoney);
        return staticSettleMoney;
    }

    @Override
    public void setLastMonthOrderSettleStaus(SharedUserVo sharedUserVo) {
        settlementOrderMapper.setLastMonthOrderSettleStaus(sharedUserVo.getShardUserId(), sharedUserVo.getStartTime(), sharedUserVo.getEndTime());
        logger.info("设置订单状态 起始时间: {}, 结束时间: {}, 用户id: {}",sharedUserVo.getStartTime(), sharedUserVo.getEndTime(), sharedUserVo.getShardUserId());
    }

    @Override
    public Map<String, Object> getStatistics(Integer sharedUserId, int dayAgo) {
        HashMap<String, Object> res = new HashMap<>();
        LocalDateTime startTime = LocalDateTime.now().minusDays(dayAgo);
        Long orderCnt = settlementOrderMapper.countOrderSharedUser(sharedUserId, startTime);
        BigDecimal orderSettleAmt = settlementOrderMapper.sumOrderSettleAmtSharedUser(sharedUserId, startTime);
        if (orderSettleAmt == null) {
            orderSettleAmt = new BigDecimal(0.00);
        }
        BigDecimal finalSettleAmt = orderSettleAmt; //默认就是设置的结算价格
        res.put("orderCnt", orderCnt);
        res.put("orderSettleAmt", orderSettleAmt);
        res.put("finalSettleAmt", finalSettleAmt);
        return res;
    }

    @Override
    public BigDecimal getUserUnOrderSettleMoney(Integer userId) {
        return settlementOrderMapper.getUserUnOrderSettleMoney(userId);
    }
}
