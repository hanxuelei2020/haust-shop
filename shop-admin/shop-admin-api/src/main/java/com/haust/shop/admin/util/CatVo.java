package com.haust.shop.admin.util;

import java.util.List;

@SuppressWarnings("rawtypes")
public class CatVo {
	private Integer value = null;
	private String label = null;
	private List children = null;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}
}
