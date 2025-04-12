package com.haust.shop.db.bean;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/** 
* @Description: 这里进行的是categoryamts的名称和值，amts的目的值控制线程安全的意思
* @Author: 韩雪磊
*/ 
public class CategorySellAmts implements Serializable{

    @Serial
    private static final long serialVersionUID = 677901688504280013L;

	private String name;
	
	private BigDecimal value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
