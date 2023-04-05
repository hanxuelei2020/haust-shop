package com.haust.shop.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

/** 
* @Description: 自定义的校验类，必须定义一个注解，然后实现JWT规范里面的ConstraintValidator<注解类，校验的类型></>类
* @Author: 韩雪磊
*/ 
public class OrderValidator implements ConstraintValidator<Order, String> {
	private List<String> valueList;

	@Override
	public void initialize(Order order) {
		valueList = new ArrayList<String>();
		for (String val : order.accepts()) {
			valueList.add(val.toUpperCase());
		}
	}

	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (!valueList.contains(s.toUpperCase())) {
			return false;
		}
		return true;
	}
}
