package com.haust.shop.admin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

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
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.dao.GoodsAllinone;
import com.haust.shop.admin.service.AdminDataAuthService;
import com.haust.shop.admin.service.AdminGoodsService;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.validator.Order;
import com.haust.shop.core.validator.Sort;
import com.haust.shop.db.domain.DtsGoods;

@RestController
@RequestMapping("/admin/goods")
@Validated
public class AdminGoodsController {
	private static final Logger logger = LoggerFactory.getLogger(AdminGoodsController.class);

	@Autowired
	private AdminGoodsService adminGoodsService;
	
	@Autowired
	private AdminDataAuthService adminDataAuthService;

	/**
	 * 查询商品
	 *
	 * @param goodsSn
	 * @param name
	 * @param page
	 * @param limit
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequiresPermissions("admin:goods:list")
	@RequiresPermissionsDesc(menu = { "商品管理", "商品管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String goodsSn, String name, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商品管理->商品管理->查询,请求参数:goodsSn:{},name:{},page:{}", goodsSn, name, page);

	    //需要区分数据权限，如果属于品牌商管理员，则需要获取当前用户管理品牌店铺
		List<Integer> brandIds = null;
		if (adminDataAuthService.isBrandManager()) {
			brandIds = adminDataAuthService.getBrandIds();
			logger.info("运营商管理角色操作，需控制数据权限，brandIds:{}",JSONObject.toJSONString(brandIds));

			if (brandIds == null || brandIds.size() == 0) {
				Map<String, Object> data = new HashMap<>();
				data.put("total", 0L);
				data.put("items", null);

				logger.info("【请求结束】商品管理->商品管理->查询,响应结果:{}", JSONObject.toJSONString(data));
				return ResponseUtil.ok(data);
			}
		}
		
		return adminGoodsService.list(goodsSn, name, page, limit, sort, order, brandIds);
	}

	@GetMapping("/catAndBrand")
	public Object catAndBrand() {
		return adminGoodsService.catAndBrand();
	}

	/**
	 * 编辑商品
	 *
	 * @param goodsAllinone
	 * @return
	 */
	@RequiresPermissions("admin:goods:update")
	@RequiresPermissionsDesc(menu = { "商品管理", "商品管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody GoodsAllinone goodsAllinone) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商品管理->商品管理->编辑,请求参数:{}", JSONObject.toJSONString(goodsAllinone));

		return adminGoodsService.update(goodsAllinone);
	}

	/**
	 * 删除商品
	 *
	 * @param goods
	 * @return
	 */
	@RequiresPermissions("admin:goods:delete")
	@RequiresPermissionsDesc(menu = { "商品管理", "商品管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsGoods goods) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商品管理->商品管理->删除,请求参数:{}", JSONObject.toJSONString(goods));

		return adminGoodsService.delete(goods);
	}

	/**
	 * 添加商品
	 *
	 * @param goodsAllinone
	 * @return
	 */
	@RequiresPermissions("admin:goods:create")
	@RequiresPermissionsDesc(menu = { "商品管理", "商品管理" }, button = "上架")
	@PostMapping("/create")
	public Object create(@RequestBody GoodsAllinone goodsAllinone) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商品管理->商品管理->上架,请求参数:{}", JSONObject.toJSONString(goodsAllinone));

		return adminGoodsService.create(goodsAllinone);
	}

	/**
	 * 商品详情
	 *
	 * @param id
	 * @return
	 */
	@RequiresPermissions("admin:goods:read")
	@RequiresPermissionsDesc(menu = { "商品管理", "商品管理" }, button = "详情")
	@GetMapping("/detail")
	public Object detail(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 商品管理->商品管理->详情,请求参数,id:{}", id);

		return adminGoodsService.detail(id);
	}

}
