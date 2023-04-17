package com.haust.shop.order.config;

import com.haust.common.consts.RocketMqConstant;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class RocketMqConfig {
    @Autowired
    private com.haust.common.config.RocketMqConfig config;

    @Bean("userOrderTemplate")
    @Lazy
    public RocketMQTemplate userOrderTemplate() {
        return config.getTemplateByTopicName(RocketMqConstant.USER_ORDER);
    }
}
