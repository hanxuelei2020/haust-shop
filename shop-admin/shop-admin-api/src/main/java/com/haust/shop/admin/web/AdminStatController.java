package com.haust.shop.admin.web;

import com.haust.service.service.order.DtsOrderService;
import com.haust.service.service.user.DtsUserService;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.admin.util.StatVo;
import com.alibaba.fastjson.JSONObject;
import com.haust.common.util.ResponseUtil;
import com.haust.service.service.stat.StatService;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/admin/stat")
@ShenyuSpringMvcClient("/admin/stat/**")
@Validated
public class AdminStatController {
	private static final Logger logger = LoggerFactory.getLogger(AdminStatController.class);

	@DubboReference
	private DtsUserService dtsUserService;

	@DubboReference
	private DtsOrderService dtsOrderService;

	@RequiresPermissions("admin:stat:user")
	@RequiresPermissionsDesc(menu = { "统计管理", "用户统计" }, button = "查询")
	@GetMapping("/user")
	public Object statUser() {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 统计管理->用户统计->查询");

		List<Map> rows = dtsUserService.statUser();
		String[] columns = new String[] { "day", "users" };
		StatVo statVo = new StatVo();
		statVo.setColumns(columns);
		statVo.setRows(rows);

		logger.info("【请求结束】统计管理->用户统计->查询,响应结果:{}", JSONObject.toJSONString(statVo));
		return ResponseUtil.ok(statVo);
	}

	@RequiresPermissions("admin:stat:order")
	@RequiresPermissionsDesc(menu = { "统计管理", "订单统计" }, button = "查询")
	@GetMapping("/order")
	public Object statOrder() {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 统计管理->订单统计->查询");

		List<Map> rows = dtsOrderService.statOrder();
		String[] columns = new String[] { "day", "orders", "customers", "amount", "pcr" };
		StatVo statVo = new StatVo();
		statVo.setColumns(columns);
		statVo.setRows(rows);

		logger.info("【请求结束】统计管理->订单统计->查询,响应结果:{}", JSONObject.toJSONString(statVo));
		return ResponseUtil.ok(statVo);
	}

	@RequiresPermissions("admin:stat:goods")
	@RequiresPermissionsDesc(menu = { "统计管理", "商品统计" }, button = "查询")
	@GetMapping("/goods")
	public Object statGoods() {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 统计管理->商品统计->查询");

		List<Map> rows = dtsOrderService.statGoods();
		String[] columns = new String[] { "day", "orders", "products", "amount" };
		StatVo statVo = new StatVo();
		statVo.setColumns(columns);
		statVo.setRows(rows);

		logger.info("【请求结束】统计管理->商品统计->查询,响应结果:{}", JSONObject.toJSONString(statVo));
		return ResponseUtil.ok(statVo);
	}

}
