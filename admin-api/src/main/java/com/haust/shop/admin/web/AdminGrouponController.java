package com.haust.shop.admin.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.service.AdminDataAuthService;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.validator.Order;
import com.haust.shop.core.validator.Sort;
import com.haust.shop.db.domain.DtsGoods;
import com.haust.shop.db.domain.DtsGroupon;
import com.haust.shop.db.domain.DtsGrouponRules;
import com.haust.shop.db.service.DtsGoodsService;
import com.haust.shop.db.service.DtsGrouponRulesService;
import com.haust.shop.db.service.DtsGrouponService;

@RestController
@RequestMapping("/admin/groupon")
@Validated
public class AdminGrouponController {
	private static final Logger logger = LoggerFactory.getLogger(AdminGrouponController.class);

	@Autowired
	private DtsGrouponRulesService rulesService;
	@Autowired
	private DtsGoodsService goodsService;
	@Autowired
	private DtsGrouponService grouponService;
	@Autowired
	private AdminDataAuthService adminDataAuthService;

	@RequiresPermissions("admin:groupon:read")
	@RequiresPermissionsDesc(menu = { "推广管理", "团购管理" }, button = "详情")
	@GetMapping("/listRecord")
	public Object listRecord(String rulesId, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->团购管理->详情,请求参数:rulesId:{},page:{}", rulesId, page);

		// 需要区分数据权限，如果属于品牌商管理员，则需要获取当前用户管理品牌店铺
		List<Integer> brandIds = null;
		if (adminDataAuthService.isBrandManager()) {
			brandIds = adminDataAuthService.getBrandIds();
			logger.info("运营商管理角色操作，需控制数据权限，brandIds:{}", JSONObject.toJSONString(brandIds));

			if (brandIds == null || brandIds.size() == 0) {// 如果尚未管理任何入驻店铺，则返回空数据
				Map<String, Object> data = new HashMap<>();
				data.put("total", 0L);
				data.put("items", null);

				logger.info("【请求结束】推广管理->团购管理->详情,响应结果:{}", JSONObject.toJSONString(data));
				return ResponseUtil.ok(data);
			}
		}
		
		List<DtsGroupon> grouponList = null;
		long total = 0L;
		if (brandIds == null || brandIds.size() == 0) {
			grouponList = grouponService.querySelective(rulesId, page, limit, sort, order);
			total = PageInfo.of(grouponList).getTotal();
		} else {
			grouponList = grouponService.queryBrandGroupons(brandIds,rulesId, page, limit, sort, order);
			total = PageInfo.of(grouponList).getTotal();
		}

		List<Map<String, Object>> records = new ArrayList<>();
		for (DtsGroupon groupon : grouponList) {
			try {
				Map<String, Object> RecordData = new HashMap<>();
				List<DtsGroupon> subGrouponList = grouponService.queryJoinRecord(groupon.getId());
				DtsGrouponRules rules = rulesService.queryById(groupon.getRulesId());
				DtsGoods goods = goodsService.findById(rules.getGoodsId().intValue());

				RecordData.put("groupon", groupon);
				RecordData.put("subGroupons", subGrouponList);
				RecordData.put("rules", rules);
				RecordData.put("goods", goods);

				records.add(RecordData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", records);

		logger.info("【请求结束】推广管理->团购管理->详情,响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	@RequiresPermissions("admin:groupon:list")
	@RequiresPermissionsDesc(menu = { "推广管理", "团购管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String goodsId, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->团购管理->查询,请求参数:goodsId:{},page:{}", goodsId, page);

		// 需要区分数据权限，如果属于品牌商管理员，则需要获取当前用户管理品牌店铺
		List<Integer> brandIds = null;
		if (adminDataAuthService.isBrandManager()) {
			brandIds = adminDataAuthService.getBrandIds();
			logger.info("运营商管理角色操作，需控制数据权限，brandIds:{}", JSONObject.toJSONString(brandIds));

			if (brandIds == null || brandIds.size() == 0) {// 如果尚未管理任何入驻店铺，则返回空数据
				Map<String, Object> data = new HashMap<>();
				data.put("total", 0L);
				data.put("items", null);
				
				logger.info("【请求结束】推广管理->团购管理->查询,响应结果:{}", JSONObject.toJSONString(data));
				return ResponseUtil.ok(data);
			}
		}
		
		List<DtsGrouponRules> rulesList = null;
		long total = 0L;
		if (brandIds == null || brandIds.size() == 0) {
			rulesList = rulesService.querySelective(goodsId, page, limit, sort, order);
			total = PageInfo.of(rulesList).getTotal();
		} else {
			rulesList = rulesService.queryBrandGrouponRules(brandIds,goodsId, page, limit, sort, order);
			total = PageInfo.of(rulesList).getTotal();
		}
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", rulesList);

		logger.info("【请求结束】推广管理->团购管理->查询,响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	private Object validate(DtsGrouponRules grouponRules) {
		Long goodsId = grouponRules.getGoodsId();
		if (goodsId == null) {
			return ResponseUtil.badArgument();
		}
		BigDecimal discount = grouponRules.getDiscount();
		if (discount == null) {
			return ResponseUtil.badArgument();
		}
		Integer discountMember = grouponRules.getDiscountMember();
		if (discountMember == null) {
			return ResponseUtil.badArgument();
		}
		LocalDateTime expireTime = grouponRules.getExpireTime();
		if (expireTime == null) {
			return ResponseUtil.badArgument();
		}

		return null;
	}

	@RequiresPermissions("admin:groupon:update")
	@RequiresPermissionsDesc(menu = { "推广管理", "团购管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody DtsGrouponRules grouponRules) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->团购管理->编辑,请求参数:{}", JSONObject.toJSONString(grouponRules));

		Object error = validate(grouponRules);
		if (error != null) {
			return error;
		}

		Integer goodsId = grouponRules.getGoodsId().intValue();
		DtsGoods goods = goodsService.findById(goodsId);
		if (goods == null) {
			return ResponseUtil.badArgumentValue();
		}

		grouponRules.setGoodsName(goods.getName());
		grouponRules.setPicUrl(goods.getPicUrl());

		if (rulesService.updateById(grouponRules) == 0) {
			logger.info("推广管理->团购管理->编辑 失败：{}", "更新数据出错！");
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】推广管理->团购管理->编辑,响应结果:{}", "成功！");
		return ResponseUtil.ok();
	}

	@RequiresPermissions("admin:groupon:create")
	@RequiresPermissionsDesc(menu = { "推广管理", "团购管理" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody DtsGrouponRules grouponRules) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->团购管理->添加,请求参数:{}", JSONObject.toJSONString(grouponRules));

		Object error = validate(grouponRules);
		if (error != null) {
			return error;
		}
		Long goodsId = grouponRules.getGoodsId();
		DtsGoods goods = null;
		/**
		 * 如果输入的值为INT范围内，则先用goodsId找,如果超出范围，
		 * 如果未找到，则转换为goodsSn找再找商品
		 */
		if ( goodsId.intValue() < Integer.MAX_VALUE) {
			goods = goodsService.findById(goodsId.intValue());
		}
		if (goods == null) {
			goods = goodsService.findByGoodsSn(goodsId.toString());
		}
		
		if (goods == null) {
			return ResponseUtil.badArgumentValue();
		}
		
		grouponRules.setGoodsId(goods.getId().longValue());//最终存库只存商品id
		grouponRules.setGoodsName(goods.getName());
		grouponRules.setPicUrl(goods.getPicUrl());

		rulesService.createRules(grouponRules);

		logger.info("【请求结束】推广管理->团购管理->添加,响应结果:{}", JSONObject.toJSONString(grouponRules));
		return ResponseUtil.ok(grouponRules);
	}

	@RequiresPermissions("admin:groupon:delete")
	@RequiresPermissionsDesc(menu = { "推广管理", "团购管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsGrouponRules grouponRules) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->团购管理->删除,请求参数:{}", JSONObject.toJSONString(grouponRules));

		Integer id = grouponRules.getId();
		if (id == null) {
			return ResponseUtil.badArgument();
		}

		rulesService.delete(id);

		logger.info("【请求结束】推广管理->团购管理->删除,响应结果:{}", "成功！");
		return ResponseUtil.ok();
	}
}
