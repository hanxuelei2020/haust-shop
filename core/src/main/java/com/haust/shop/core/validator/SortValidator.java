package com.haust.shop.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class SortValidator implements ConstraintValidator<Sort, String> {
	private List<String> valueList;

	@Override
	public void initialize(Sort sort) {
		valueList = new ArrayList<String>();
		for (String val : sort.accepts()) {
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
