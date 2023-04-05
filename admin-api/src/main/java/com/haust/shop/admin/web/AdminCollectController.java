package com.haust.shop.admin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.validator.Order;
import com.haust.shop.core.validator.Sort;
import com.haust.shop.db.domain.DtsCollect;
import com.haust.shop.db.service.DtsCollectService;

@RestController
@RequestMapping("/admin/collect")
@Validated
public class AdminCollectController {
	private static final Logger logger = LoggerFactory.getLogger(AdminCollectController.class);

	@Autowired
	private DtsCollectService collectService;

	@RequiresPermissions("admin:collect:list")
	@RequiresPermissionsDesc(menu = { "用户管理", "用户收藏" }, button = "查询")
	@GetMapping("/list")
	public Object list(String userId, String valueId, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 用户管理->用户收藏->查询,请求参数:userId:{},page:{}", userId, page);

		List<DtsCollect> collectList = collectService.querySelective(userId, valueId, page, limit, sort, order);
		long total = PageInfo.of(collectList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", collectList);

		logger.info("【请求结束】用户管理->用户收藏->查询:total:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}
}
