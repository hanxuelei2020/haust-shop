package com.haust.shop.user.config;

import com.haust.common.consts.RocketMqConstant;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class UserRocketMqConfig {
    @Autowired
    private com.haust.common.config.RocketMqConfig config;

    @Bean("userOrderTemplate")
    @Lazy
    public RocketMQTemplate userSettle() {
        return config.getTemplateByTopicName(RocketMqConstant.USER_ORDER);
    }

}
