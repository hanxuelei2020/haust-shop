package com.haust.shop.order.config;

import com.haust.common.consts.RocketMqConstant;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class OrderRocketMqConfig {
    @Autowired
    private com.haust.common.config.RocketMqConfig config;

    @Bean("orderPayTemplate")
    @Lazy
    public RocketMQTemplate userSettle() {
        return config.getTemplateByTopicName(RocketMqConstant.ORDER_NOTIFY_TOPIC);
    }

}
