package com.haust.shop.admin.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.common.consts.CouponConstant;
import com.haust.common.util.ResponseUtil;
import com.haust.common.validator.Order;
import com.haust.common.validator.Sort;
import com.haust.service.domain.coupon.DtsCoupon;
import com.haust.service.domain.coupon.DtsCouponUser;
import com.haust.service.service.coupon.DtsCouponService;
import com.haust.service.service.coupon.DtsCouponUserService;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/coupon")
@Validated
public class AdminCouponController {
	private static final Logger logger = LoggerFactory.getLogger(AdminCouponController.class);

	@DubboReference
	private DtsCouponService couponService;
	@DubboReference
	private DtsCouponUserService couponUserService;

	@RequiresPermissions("admin:coupon:list")
	@RequiresPermissionsDesc(menu = { "推广管理", "优惠券管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String name, Short type, Short status, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->优惠券管理->查询,请求参数:name:{},page:{}", name, page);

		List<DtsCoupon> couponList = couponService.querySelective(name, type, status, page, limit, sort, order);
		long total = PageInfo.of(couponList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", couponList);

		logger.info("【请求结束】推广管理->优惠券管理->查询:响应结果:{}", "成功!");
		return ResponseUtil.ok(data);
	}

	@RequiresPermissions("admin:coupon:listuser")
	@RequiresPermissionsDesc(menu = { "推广管理", "优惠券管理" }, button = "查询用户")
	@GetMapping("/listuser")
	public Object listuser(Integer userId, Integer couponId, Short status,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->优惠券管理->查询用户,请求参数:userId:{},couponId:{}", userId, couponId);

		List<DtsCouponUser> couponList = couponUserService.queryList(userId, couponId, status, page, limit, sort,
				order);
		long total = PageInfo.of(couponList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", couponList);

		logger.info("【请求结束】推广管理->优惠券管理->查询用户:响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	private Object validate(DtsCoupon coupon) {
		String name = coupon.getName();
		if (StringUtils.isEmpty(name)) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	@RequiresPermissions("admin:coupon:create")
	@RequiresPermissionsDesc(menu = { "推广管理", "优惠券管理" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody DtsCoupon coupon) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->优惠券管理->添加,请求参数:{}", JSONObject.toJSONString(coupon));

		Object error = validate(coupon);
		if (error != null) {
			return error;
		}

		// 如果是兑换码类型，则这里需要生存一个兑换码
		if (coupon.getType().equals(CouponConstant.TYPE_CODE)) {
			String code = couponService.generateCode();
			coupon.setCode(code);
		}

		couponService.add(coupon);

		logger.info("【请求结束】推广管理->优惠券管理->添加,响应结果:{}", JSONObject.toJSONString(coupon));
		return ResponseUtil.ok(coupon);
	}

	@RequiresPermissions("admin:coupon:read")
	@RequiresPermissionsDesc(menu = { "推广管理", "优惠券管理" }, button = "详情")
	@GetMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->优惠券管理->详情,请求参数,id:{}", id);

		DtsCoupon coupon = couponService.findById(id);

		logger.info("【请求结束】推广管理->优惠券管理->详情,响应结果:{}", JSONObject.toJSONString(coupon));
		return ResponseUtil.ok(coupon);
	}

	@RequiresPermissions("admin:coupon:update")
	@RequiresPermissionsDesc(menu = { "推广管理", "优惠券管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody DtsCoupon coupon) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->优惠券管理->编辑,请求参数:{}", JSONObject.toJSONString(coupon));

		Object error = validate(coupon);
		if (error != null) {
			return error;
		}
		if (couponService.updateById(coupon) == 0) {
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】推广管理->优惠券管理->编辑,响应结果:{}", JSONObject.toJSONString(coupon));
		return ResponseUtil.ok(coupon);
	}

	@RequiresPermissions("admin:coupon:delete")
	@RequiresPermissionsDesc(menu = { "推广管理", "优惠券管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsCoupon coupon) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->优惠券管理->删除,请求参数:{}", JSONObject.toJSONString(coupon));

		couponService.deleteById(coupon.getId());

		logger.info("【请求结束】推广管理->优惠券管理->删除,响应结果:{}", "成功!");
		return ResponseUtil.ok();
	}

}
