package com.haust.service.service.order;

import com.haust.service.domain.order.DtsOrder;
import com.haust.service.domain.order.SharedUserVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SettlementOrderService {
    public BigDecimal getMonthSettleMoney(Integer sharedUserId, String startTime, String endTime);
    public List<DtsOrder> querySettlementOrder(Integer sharedUserId, List<Short> orderStatus,
                                               List<Short> settlementStatus, Integer page, Integer size);
    public BigDecimal getUnSettleAmount(Integer userId);
    public BigDecimal getUnSettleAmount(Integer userId,String startDay,String endDay);

    void setLastMonthOrderSettleStaus(SharedUserVo sharedUserVo);

    Map<String, Object>  getStatistics(Integer sharedUserId, int dayAgo);

    BigDecimal getUserUnOrderSettleMoney(Integer userId);
}
