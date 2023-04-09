package com.haust.shop.admin.service.impl;

import com.haust.common.util.ResponseUtil;
import com.haust.service.domain.admin.DtsAdmin;
import com.haust.service.domain.product.DtsCategory;
import com.haust.service.service.admin.DtsAdminService;
import com.haust.service.service.product.DtsCategoryService;
import com.haust.shop.admin.service.AdminBrandService;
import com.haust.shop.admin.util.CatVo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminBrandServiceImpl implements AdminBrandService {
	private static final Logger logger = LoggerFactory.getLogger(AdminBrandService.class);

	@DubboReference
	private DtsCategoryService categoryService;
	
	@Autowired
	private DtsAdminService adminService;

	/**
	 * 获取分类和管理用户
	 * @return
	 */
	public Object catAndAdmin() {
		List<DtsCategory> l1CatList = categoryService.queryL1();
		List<CatVo> categoryList = new ArrayList<CatVo>(l1CatList == null ? 0 : l1CatList.size());

		for (DtsCategory l1 : l1CatList) {
			CatVo l1CatVo = new CatVo();
			l1CatVo.setValue(l1.getId());
			l1CatVo.setLabel(l1.getName());

			List<DtsCategory> l2CatList = categoryService.queryByPid(l1.getId());
			List<CatVo> children = new ArrayList<CatVo>(l2CatList == null ? 0 : l2CatList.size());
			for (DtsCategory l2 : l2CatList) {
				CatVo l2CatVo = new CatVo();
				l2CatVo.setValue(l2.getId());
				l2CatVo.setLabel(l2.getName());
				children.add(l2CatVo);
			}
			l1CatVo.setChildren(children);

			categoryList.add(l1CatVo);
		}
		
		//系统用户
		List<DtsAdmin> list = adminService.allAdmin();
		List<Map<String, Object>> adminList = new ArrayList<Map<String, Object>>(list == null ? 0 : list.size());
		for (DtsAdmin admin : list) {
			Map<String, Object> b = new HashMap<>(2);
			b.put("value", admin.getId());
			b.put("label", admin.getUsername());
			adminList.add(b);
		}

		Map<String, Object> data = new HashMap<>();
		data.put("categoryList", categoryList);
		data.put("adminList", adminList);
		logger.info("品牌入驻获取的总一级目录数：{},总会员数：{}",categoryList.size(),adminList.size());
		return ResponseUtil.ok(data);
	}

}
