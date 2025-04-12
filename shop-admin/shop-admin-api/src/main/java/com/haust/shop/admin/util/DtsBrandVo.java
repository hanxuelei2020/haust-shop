package com.haust.shop.admin.util;


import com.haust.service.domain.product.DtsBrand;

import java.io.Serializable;

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
