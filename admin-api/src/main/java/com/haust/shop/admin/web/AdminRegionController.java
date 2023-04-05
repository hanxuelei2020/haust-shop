package com.haust.shop.admin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.validator.Order;
import com.haust.shop.core.validator.Sort;
import com.haust.shop.db.domain.DtsRegion;
import com.haust.shop.db.service.DtsRegionService;

@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
	private static final Logger logger = LoggerFactory.getLogger(AdminRegionController.class);

	@Autowired
	private DtsRegionService regionService;

	@GetMapping("/clist")
	public Object clist(@NotNull Integer id) {
		List<DtsRegion> regionList = regionService.queryByPid(id);
		return ResponseUtil.ok(regionList);
	}

	@GetMapping("/list")
	public Object list(String name, Integer code, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort(accepts = { "id" }) @RequestParam(defaultValue = "id") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 行政区域管理->查询,请求参数,name:{},code:{},page:{}", name, code, page);

		List<DtsRegion> regionList = regionService.querySelective(name, code, page, limit, sort, order);
		long total = PageInfo.of(regionList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", regionList);

		logger.info("【请求结束】行政区域管理->查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}
}
