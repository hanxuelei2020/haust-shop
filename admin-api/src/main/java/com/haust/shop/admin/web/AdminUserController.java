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
import com.github.pagehelper.PageInfo;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.core.qcode.QCodeService;
import com.haust.shop.core.util.JacksonUtil;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.validator.Order;
import com.haust.shop.core.validator.Sort;
import com.haust.shop.db.domain.DtsUser;
import com.haust.shop.db.domain.DtsUserAccount;
import com.haust.shop.db.service.DtsAccountService;
import com.haust.shop.db.service.DtsUserService;

@RestController
@RequestMapping("/admin/user")
@Validated
public class AdminUserController {
	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	@Autowired
	private DtsUserService userService;
	
	@Autowired
	private QCodeService qCodeService;
	
	@Autowired
	private DtsAccountService accountService;

	@RequiresPermissions("admin:user:list")
	@RequiresPermissionsDesc(menu = { "用户管理", "会员管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String username, String mobile, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 用户管理->会员管理->查询,请求参数,username:{},code:{},page:{}", username, mobile, page);

		List<DtsUser> userList = userService.querySelective(username, mobile, page, limit, sort, order);
		long total = PageInfo.of(userList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", userList);

		logger.info("【请求结束】用户管理->会员管理->查询:响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}
	
	/**
	 * 订单详情
	 *
	 * @param id
	 * @return
	 */
	@RequiresPermissions("admin:user:read")
	@RequiresPermissionsDesc(menu = { "用户管理", "会员管理" }, button = "代理详情")
	@GetMapping("/detailApprove")
	public Object detailApprove(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 用户管理->会员管理->代理详情,请求参数:id:{}", id);
		
		DtsUserAccount dbAccount = userService.detailApproveByUserId(id);
		if (dbAccount == null) {
			logger.error("用户管理->会员管理->代理详情 错误：userID:{},{}",id,"代理账号信息不存在");
			ResponseUtil.badArgumentValue();
		}
		logger.info("【请求结束】用户管理->会员管理->代理详情:响应结果:{}", JSONObject.toJSONString(dbAccount));
		return ResponseUtil.ok(dbAccount);
	}
	
	@RequiresPermissions("admin:user:approveAgency")
	@RequiresPermissionsDesc(menu = { "用户管理", "会员管理" }, button = "代理审批")
	@PostMapping("/approveAgency")
	public Object approveAgency(@RequestBody String body) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 用户管理->会员管理->代理审批,请求参数:{}",body);
		
		Integer userId = JacksonUtil.parseInteger(body, "userId");
		Integer settlementRate = JacksonUtil.parseInteger(body, "settlementRate");
		
		if (userId == null || settlementRate == null || settlementRate.intValue() <= 0 ){
			return ResponseUtil.badArgument();
		}
		try {
			/*
			 * 生成代理用户独有分享的二维码需要小程序已经上线，所以未上线小程序这里调用会异常
			 * 建议通过后台参数控制，因为定制用户对这里的特殊性要求，本程序暂不做调整
			 */
			String shareUrl = qCodeService.createShareUserImage(userId);
			
			/**
			 * 结算当前用户的订单佣金给其代理
			 * 在用户审批通过成为代理用户之前下的订单，结算佣金应归属于前一个代理用户
			 * 后续的订单由用户申请或系统自动结算给，代理用户直接会将佣金结算给自己
			 */
			boolean result = accountService.settlementPreviousAgency(userId);
			if (!result) {
				logger.warn("用户管理->会员管理->代理审批 存在异常：{}","当前用户订单佣金交割给代理用户时出错！");
			}
			
			userService.approveAgency(userId,settlementRate,shareUrl);
			
		}catch (Exception e) {
			logger.error("用户管理->会员管理->代理审批 出错：{}",e.getMessage());
			e.printStackTrace();
		}
		
		logger.info("【请求结束】用户管理->会员管理->代理审批:响应结果:{}", "成功!");
		return ResponseUtil.ok();
	}
	
}
