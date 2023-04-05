package com.haust.shop.admin.util;

import java.io.Serializable;

import com.haust.shop.db.domain.DtsBrand;

public class DtsBrandVo extends DtsBrand implements Serializable{

	private static final long serialVersionUID = 6530090986580196500L;
	
	private Integer[] categoryIds;

	public Integer[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(Integer[] categoryIds) {
		this.categoryIds = categoryIds;
	}
	
	

}
