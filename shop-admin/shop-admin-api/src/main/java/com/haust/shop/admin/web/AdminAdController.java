package com.haust.shop.admin.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.common.util.ResponseUtil;
import com.haust.common.validator.Order;
import com.haust.common.validator.Sort;
import com.haust.service.domain.product.DtsAd;
import com.haust.service.service.product.DtsAdService;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/ad")
@Validated
@ShenyuSpringMvcClient("/admin/ad/**")
public class AdminAdController {
	private static final Logger logger = LoggerFactory.getLogger(AdminAdController.class);

	@DubboReference
	private DtsAdService adService;

	@RequiresPermissions("admin:ad:list")
	@RequiresPermissionsDesc(menu = { "推广管理", "广告管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String name, String content, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 操作人:[" + AuthSupport.userName()+ "] 推广管理->广告管理->查询,请求参数:name:{},content:{},page:{}", name, content, page);

		List<DtsAd> adList = adService.querySelective(name, content, page, limit, sort, order);
		long total = PageInfo.of(adList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", adList);

		logger.info("【请求结束】推广管理->广告管理->查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	private Object validate(DtsAd ad) {
		String name = ad.getName();
		if (StringUtils.isEmpty(name)) {
			return ResponseUtil.badArgument();
		}
		String content = ad.getContent();
		if (StringUtils.isEmpty(content)) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	@RequiresPermissions("admin:ad:create")
	@RequiresPermissionsDesc(menu = { "推广管理", "广告管理" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody DtsAd ad) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->广告管理->添加,请求参数:ad:{}", JSONObject.toJSONString(ad));

		Object error = validate(ad);
		if (error != null) {
			logger.error("广告管理 添加校验不通过!");
			return error;
		}
		adService.add(ad);

		logger.info("【请求结束】推广管理->广告管理->添加,响应结果:{}", JSONObject.toJSONString(ad));
		return ResponseUtil.ok(ad);
	}

	@RequiresPermissions("admin:ad:read")
	@RequiresPermissionsDesc(menu = { "推广管理", "广告管理" }, button = "详情")
	@GetMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->广告管理->详情,请求参数:id:{}", id);

		DtsAd brand = adService.findById(id);

		logger.info("【请求结束】推广管理->广告管理->详情,响应结果:{}", JSONObject.toJSONString(brand));
		return ResponseUtil.ok(brand);
	}

	@RequiresPermissions("admin:ad:update")
	@RequiresPermissionsDesc(menu = { "推广管理", "广告管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody DtsAd ad) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->广告管理->编辑,请求参数:ad:{}", JSONObject.toJSONString(ad));

		Object error = validate(ad);
		if (error != null) {
			return error;
		}
		if (adService.updateById(ad) == 0) {
			logger.info("推广管理->广告管理->编辑,更新广告数据失败!");
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】推广管理->广告管理->编辑,响应结果:{}", JSONObject.toJSONString(ad));
		return ResponseUtil.ok(ad);
	}

	@RequiresPermissions("admin:ad:delete")
	@RequiresPermissionsDesc(menu = { "推广管理", "广告管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsAd ad) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->广告管理->删除,请求参数:ad:{}", JSONObject.toJSONString(ad));

		Integer id = ad.getId();
		if (id == null) {
			return ResponseUtil.badArgument();
		}
		adService.deleteById(id);

		logger.info("【请求结束】推广管理->广告管理->删除,响应结果:{}", "成功");
		return ResponseUtil.ok();
	}

}
