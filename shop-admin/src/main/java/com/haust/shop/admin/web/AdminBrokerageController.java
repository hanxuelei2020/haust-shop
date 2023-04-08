package com.haust.shop.admin.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.common.util.JacksonUtil;
import com.haust.common.util.ResponseUtil;
import com.haust.common.validator.Order;
import com.haust.common.validator.Sort;
import com.haust.service.domain.user.DtsAccountTrace;
import com.haust.service.domain.user.DtsUser;
import com.haust.service.service.user.DtsAccountService;
import com.haust.service.service.user.DtsUserService;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import com.haust.shop.admin.util.AuthSupport;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@DubboReference
	private DtsAccountService accountService;

	@DubboReference
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
