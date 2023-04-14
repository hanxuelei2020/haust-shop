package com.haust.shop.user.controller;

import com.haust.common.util.ResponseUtil;
import org.apache.shenyu.client.springmvc.annotation.ShenyuSpringMvcClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试服务
 */
@RestController
@RequestMapping("/wx/index")
@ShenyuSpringMvcClient("/wx/index/**")
public class WxIndexController {

	/**
	 * 测试数据
	 *
	 * @return 测试数据
	 */
	@RequestMapping("/index")
	public Object index() {
		return ResponseUtil.ok("hello dts");
	}

}