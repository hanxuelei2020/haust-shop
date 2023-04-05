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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import com.haust.shop.core.util.JacksonUtil;
import com.haust.shop.core.util.ResponseUtil;
import com.haust.shop.core.validator.Order;
import com.haust.shop.core.validator.Sort;
import com.haust.shop.db.domain.DtsAccountTrace;
import com.haust.shop.db.domain.DtsUser;
import com.haust.shop.db.service.DtsAccountService;
import com.haust.shop.db.service.DtsUserService;

/**
 * 佣金业务接口
 * 
 * @since 1.0.0
 * @author 韩雪磊
 * @QQ 2407411399
 */
@RestController
@RequestMapping("/admin/brokerage")
@Validated
public class AdminBrokerageController {
	private static final Logger logger = LoggerFactory.getLogger(AdminBrokerageController.class);

	@Autowired
	private DtsAccountService accountService;
	
	@Autowired
	private DtsUserService userService;

	@RequiresPermissions("admin:brokerage:list")
	@RequiresPermissionsDesc(menu = { "用户管理", "佣金管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String username, String mobile,@RequestParam(required = false) List<Byte> statusArray,@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 用户管理->佣金管理->查询,请求参数,username:{},mobile:{},status:{},page:{}", username, mobile, JSONObject.toJSONString(statusArray),page);

		List<DtsUser> userList = userService.queryDtsUserListByNickname(username, mobile);
		List<DtsAccountTrace> traceList = accountService.querySelectiveTrace(userList,statusArray, page, limit, sort, order);
		long total = PageInfo.of(traceList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("traceList", traceList);

		logger.info("【请求结束】用户管理->佣金管理->查询:记录数:{}", traceList == null ? 0 : traceList.size());
		return ResponseUtil.ok(data);
	}

	@RequiresPermissions("admin:brokerage:approve")
	@RequiresPermissionsDesc(menu = { "用户管理", "佣金管理" }, button = "审批销账")
	@PostMapping("/approve")
	public Object approve(@RequestBody String body) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 用户管理->佣金管理->审批销账,请求参数:{}",body);
		Integer traceId = JacksonUtil.parseInteger(body, "id");
		String traceMsg = JacksonUtil.parseString(body, "traceMsg");
		Byte status = JacksonUtil.parseByte(body, "status");
		boolean approveResult = accountService.approveAccountTrace(traceId,status, traceMsg);
		
		if (!approveResult) {
			logger.info("用户管理->佣金管理->审批销账失败：{}", "审批处理错误！");
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】用户管理->佣金管理->审批销账,响应结果:{}", "成功！");
		return ResponseUtil.ok();
		
	}
}
