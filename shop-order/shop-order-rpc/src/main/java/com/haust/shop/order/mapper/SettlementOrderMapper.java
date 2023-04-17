package com.haust.shop.order.mapper;

import com.haust.service.domain.order.DtsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SettlementOrderMapper {
    BigDecimal getToSettleMoney(@Param("sharedUserId") Integer sharedUserId,
                                @Param("startTime") String startTime, @Param("endTime") String endTime);

    void setLastMonthOrderSettleStaus(@Param("sharedUserId") Integer sharedUserId, @Param("startTime") String startTime,
                                      @Param("endTime") String endTime);
    BigDecimal staticMonthSettleMoney(@Param("sharedUserId") Integer sharedUserId, @Param("startTime") String startTime,
                                      @Param("endTime") String endTime);
    Long countOrderSharedUser(@Param("sharedUserId") Integer sharedUserId, @Param("startTime") LocalDateTime startTime);
    BigDecimal sumOrderSettleAmtSharedUser(@Param("sharedUserId") Integer sharedUserId,
                                           @Param("startTime") LocalDateTime startTime);
    List<DtsOrder> querySettlementOrder(@Param("sharedUserId") Integer sharedUserId,
                                        @Param("conditionSql") String conditionSql);
    /**
     * 获取用户的未结算佣金
     * @param userId
     * @return
     */
    BigDecimal getUserUnOrderSettleMoney(@Param("userId") Integer userId);

    /**
     * 将用户订单的的状态调整为已结算
     * @param userId
     */
    void setUserOrderSettleStaus(@Param("userId") Integer userId);
}
