package com.haust.shop.admin.web;

import com.haust.shop.admin.util.AuthSupport;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.common.util.ResponseUtil;
import com.haust.common.validator.Order;
import com.haust.common.validator.Sort;
import com.haust.service.domain.product.DtsTopic;
import com.haust.service.service.admin.DtsTopicService;
import com.haust.service.service.third.QCodeService;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/topic")
@ShenyuSpringMvcClient("/admin/topic/**")
@Validated
public class AdminTopicController {
	private static final Logger logger = LoggerFactory.getLogger(AdminTopicController.class);

	@Autowired
	private DtsTopicService topicService;

	@DubboReference
	private QCodeService qCodeService;
	
	@RequiresPermissions("admin:topic:list")
	@RequiresPermissionsDesc(menu = { "推广管理", "专题管理" }, button = "查询")
	@GetMapping("/list")
	public Object list(String title, String subtitle, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->专题管理->查询,请求参数,title:{},subtitle:{},page:{}", title, subtitle, page);

		List<DtsTopic> topicList = topicService.querySelective(title, subtitle, page, limit, sort, order);
		long total = PageInfo.of(topicList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", topicList);

		logger.info("【请求结束】推广管理->专题管理->查询:响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	private Object validate(DtsTopic topic) {
		String title = topic.getTitle();
		if (StringUtils.isEmpty(title)) {
			return ResponseUtil.badArgument();
		}
		String content = topic.getContent();
		if (StringUtils.isEmpty(content)) {
			return ResponseUtil.badArgument();
		}
		BigDecimal price = topic.getPrice();
		if (price == null) {
			return ResponseUtil.badArgument();
		}
		return null;
	}

	@RequiresPermissions("admin:topic:create")
	@RequiresPermissionsDesc(menu = { "推广管理", "专题管理" }, button = "添加")
	@PostMapping("/create")
	public Object create(@RequestBody DtsTopic topic) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->专题管理->添加,请求参数:{}", JSONObject.toJSONString(topic));

		Object error = validate(topic);
		if (error != null) {
			return error;
		}
		try {
			//生成主题的分享URL
			String shareUrl = qCodeService.createShareTopicImage(topic.getId(), topic.getPicUrl(), topic.getSubtitle(),topic.getPrice());
			topic.setShareUrl(shareUrl);
		} catch (Exception e) {
			logger.error("专题生成分享图URL出错：{}",e.getMessage());
			e.printStackTrace();
		}
		
		topicService.add(topic);

		logger.info("【请求结束】推广管理->专题管理->添加:响应结果:{}", JSONObject.toJSONString(topic));
		return ResponseUtil.ok(topic);
	}

	@RequiresPermissions("admin:topic:read")
	@RequiresPermissionsDesc(menu = { "推广管理", "专题管理" }, button = "详情")
	@GetMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->专题管理->详情,请求参数,id:{}", id);

		DtsTopic topic = topicService.findById(id);

		logger.info("【请求结束】推广管理->专题管理->详情:响应结果:{}", JSONObject.toJSONString(topic));
		return ResponseUtil.ok(topic);
	}

	@RequiresPermissions("admin:topic:update")
	@RequiresPermissionsDesc(menu = { "推广管理", "专题管理" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody DtsTopic topic) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->专题管理->编辑,请求参数:{}", JSONObject.toJSONString(topic));

		Object error = validate(topic);
		if (error != null) {
			return error;
		}
		try {
			//生成主题的分享URL
			String shareUrl = qCodeService.createShareTopicImage(topic.getId(), topic.getPicUrl(), topic.getSubtitle(),topic.getPrice());
			topic.setShareUrl(shareUrl);
		} catch (Exception e) {
			logger.error("专题生成分享图URL出错：{}",e.getMessage());
			e.printStackTrace();
		}
		
		if (topicService.updateById(topic) == 0) {
			logger.error("推广管理->专题管理->编辑 错误:{}", "更新数据失败!");
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】推广管理->专题管理->编辑,响应结果:{}", JSONObject.toJSONString(topic));
		return ResponseUtil.ok(topic);
	}

	@RequiresPermissions("admin:topic:delete")
	@RequiresPermissionsDesc(menu = { "推广管理", "专题管理" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsTopic topic) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 推广管理->专题管理->删除,请求参数:{}", JSONObject.toJSONString(topic));

		topicService.deleteById(topic.getId());

		logger.info("【请求结束】推广管理->专题管理->删除,响应结果:{}", "成功!");
		return ResponseUtil.ok();
	}

}
