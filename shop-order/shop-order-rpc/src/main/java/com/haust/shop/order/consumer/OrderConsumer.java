package com.haust.shop.order.consumer;

import com.haust.common.consts.RocketMqConstant;
import com.haust.service.service.order.SettlementOrderService;
import com.haust.service.domain.order.SharedUserVo;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 解锁库存的监听
 * @author FrozenWatermelon
 */
@Component
@RocketMQMessageListener(topic = RocketMqConstant.STOCK_UNLOCK_TOPIC,consumerGroup = RocketMqConstant.STOCK_UNLOCK_TOPIC)
public class OrderConsumer implements RocketMQListener<SharedUserVo>{

    @Autowired
    private SettlementOrderService settlementOrderService;

    /**
     * 更新订单结算状态
     */
    @Override
    public void onMessage(SharedUserVo sharedUserVo) {
        settlementOrderService.setLastMonthOrderSettleStaus(sharedUserVo);
    }
}
