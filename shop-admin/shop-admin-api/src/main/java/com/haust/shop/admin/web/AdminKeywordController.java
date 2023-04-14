package com.haust.shop.admin.web;

import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.common.util.ResponseUtil;
import com.haust.common.validator.Order;
import com.haust.common.validator.Sort;
import com.haust.service.domain.search.DtsKeyword;
import com.haust.service.service.search.DtsKeywordService;
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
@RequestMapping("/admin/keyword")
@ShenyuSpringMvcClient("/admin/keyword/**")
@Validated
public class AdminKeywordController {
	private static final Logger logger = LoggerFactory.getLogger(AdminKeywordController.class);

	@DubboReference
	private DtsKeywordService keywordService;

	@RequiresPermissions("admin:keyword:list")
	@RequiresPermissionsDesc(menu = { "商场管理", "关键词" }, button = "查询")
	@GetMapping("/list")
	public Object list(String keyword, String url, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商场管理->关键词->查询,请求参数:keyword:{},url:{},page:{}", keyword, url, page);

		List<DtsKeyword> brandList = keywordService.querySelective(keyword, url, page, limit, sort, order);
		long total = PageInfo.of(brandList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", brandList);

		logger.info("【请求结束】商场管理->关键词->查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	private Object validate(DtsKeyword keywords) {
		String keyword = keywords.getKeyword();
		if (StringUtils.isEmpty(keyword)) {
			return ResponseUtil.badArgument();
		}
		String url = keywords.getUrl();
		if (StringUtils.isEmpty(url)) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	@RequiresPermissions("admin:keyword:create")
	@RequiresPermissionsDesc(menu = { "商场管理", "关键词" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody DtsKeyword keywords) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商场管理->关键词->添加,请求参数:{}", JSONObject.toJSONString(keywords));

		Object error = validate(keywords);
		if (error != null) {
			return error;
		}
		keywordService.add(keywords);

		logger.info("【请求结束】商场管理->关键词->添加,响应结果:{}", JSONObject.toJSONString(keywords));
		return ResponseUtil.ok(keywords);
	}

	@RequiresPermissions("admin:keyword:read")
	@RequiresPermissionsDesc(menu = { "商场管理", "关键词" }, button = "详情")
	@GetMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商场管理->关键词->详情,请求参数,id:{}", id);

		DtsKeyword keywords = keywordService.findById(id);

		logger.info("【请求结束】商场管理->关键词->详情,响应结果:{}", JSONObject.toJSONString(keywords));
		return ResponseUtil.ok(keywords);
	}

	@RequiresPermissions("admin:keyword:update")
	@RequiresPermissionsDesc(menu = { "商场管理", "关键词" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody DtsKeyword keywords) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商场管理->关键词->编辑,请求参数:{}", JSONObject.toJSONString(keywords));

		Object error = validate(keywords);
		if (error != null) {
			return error;
		}
		if (keywordService.updateById(keywords) == 0) {
			logger.info("商场管理->关键词->编辑 错误：{}", "更新数据失败！");
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】商场管理->关键词->编辑,响应结果:{}", JSONObject.toJSONString(keywords));
		return ResponseUtil.ok(keywords);
	}

	@RequiresPermissions("admin:keyword:delete")
	@RequiresPermissionsDesc(menu = { "商场管理", "关键词" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsKeyword keyword) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商场管理->关键词->删除,请求参数:{}", JSONObject.toJSONString(keyword));

		Integer id = keyword.getId();
		if (id == null) {
			return ResponseUtil.badArgument();
		}
		keywordService.deleteById(id);

		logger.info("【请求结束】商场管理->关键词->删除,响应结果:{}", "成功!");
		return ResponseUtil.ok();
	}

}
