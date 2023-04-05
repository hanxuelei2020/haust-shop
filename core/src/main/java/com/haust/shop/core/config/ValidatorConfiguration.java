package com.haust.shop.core.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 这里配置的是校验，JWT规范，使用的是hibernate实现的校验规则。
 * 即数据非空校验之类的，如果自己自主进行校验比较麻烦，采用工具完成
 */
@Configuration
public class ValidatorConfiguration {
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
				.addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		return validator;
	}
}
