package com.haust.shop.admin.web;

import com.haust.shop.admin.util.AuthSupport;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.haust.common.util.ResponseUtil;
import com.haust.common.validator.Order;
import com.haust.common.validator.Sort;
import com.haust.service.domain.third.DtsStorage;
import com.haust.service.service.third.DtsStorageService;
import com.haust.service.service.third.StorageService;
import com.haust.shop.admin.annotation.RequiresPermissionsDesc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/com/haust/shop/admin/storage")
@Validated
public class AdminStorageController {
	private static final Logger logger = LoggerFactory.getLogger(AdminStorageController.class);

	@DubboReference
	private StorageService storageService;
	@DubboReference
	private DtsStorageService dtsStorageService;

	@RequiresPermissions("admin:storage:list")
	@RequiresPermissionsDesc(menu = { "系统管理", "对象存储" }, button = "查询")
	@GetMapping("/list")
	public Object list(String key, String name, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer limit,
			@Sort @RequestParam(defaultValue = "add_time") String sort,
			@Order @RequestParam(defaultValue = "desc") String order) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->对象存储->查询,请求参数,name:{},key:{},page:{}", name, key, page);

		List<DtsStorage> storageList = dtsStorageService.querySelective(key, name, page, limit, sort, order);
		long total = PageInfo.of(storageList).getTotal();
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("items", storageList);

		logger.info("【请求结束】系统管理->对象存储->查询:响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	@RequiresPermissions("admin:storage:create")
	@RequiresPermissionsDesc(menu = { "系统管理", "对象存储" }, button = "上传")
	@PostMapping("/create")
	public Object create(@RequestParam("file") MultipartFile file) throws IOException {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->对象存储->上传,请求参数,file:{}", file.getOriginalFilename());

		String originalFilename = file.getOriginalFilename();
		String url = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(),
				originalFilename);
		Map<String, Object> data = new HashMap<>();
		data.put("url", url);

		logger.info("【请求结束】系统管理->对象存储->查询:响应结果:{}", JSONObject.toJSONString(data));
		return ResponseUtil.ok(data);
	}

	@RequiresPermissions("admin:storage:read")
	@RequiresPermissionsDesc(menu = { "系统管理", "对象存储" }, button = "详情")
	@PostMapping("/read")
	public Object read(@NotNull Integer id) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->对象存储->详情,请求参数,id:{}", id);

		DtsStorage storageInfo = dtsStorageService.findById(id);
		if (storageInfo == null) {
			return ResponseUtil.badArgumentValue();
		}

		logger.info("【请求结束】系统管理->对象存储->详情:响应结果:{}", JSONObject.toJSONString(storageInfo));
		return ResponseUtil.ok(storageInfo);
	}

	@RequiresPermissions("admin:storage:update")
	@RequiresPermissionsDesc(menu = { "系统管理", "对象存储" }, button = "编辑")
	@PostMapping("/update")
	public Object update(@RequestBody DtsStorage dtsStorage) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->对象存储->编辑,请求参数:{}", JSONObject.toJSONString(dtsStorage));

		if (dtsStorageService.update(dtsStorage) == 0) {
			logger.error("系统管理->对象存储->编辑 错误:{}", "更新数据失败!");
			return ResponseUtil.updatedDataFailed();
		}

		logger.info("【请求结束】系统管理->对象存储->编辑:响应结果:{}", JSONObject.toJSONString(dtsStorage));
		return ResponseUtil.ok(dtsStorage);
	}

	@RequiresPermissions("admin:storage:delete")
	@RequiresPermissionsDesc(menu = { "系统管理", "对象存储" }, button = "删除")
	@PostMapping("/delete")
	public Object delete(@RequestBody DtsStorage DtsStorage) {
		logger.info("【请求开始】操作人:[" + AuthSupport.userName()+ "] 系统管理->对象存储->删除,请求参数:{}", JSONObject.toJSONString(DtsStorage));

		String key = DtsStorage.getKey();
		if (StringUtils.isEmpty(key)) {
			return ResponseUtil.badArgument();
		}
		dtsStorageService.deleteByKey(key);
		storageService.delete(key);

		logger.info("【请求结束】系统管理->对象存储->删除:响应结果:{}", "成功!");
		return ResponseUtil.ok();
	}
}
