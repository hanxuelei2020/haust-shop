package com.haust.shop.wx.util;

import com.haust.shop.core.util.ResponseUtil;

/**
 * 微信接口枚举信息的响应
 * 
 * @author 韩雪磊
 * @since 1.0.0
 */
public class WxResponseUtil extends ResponseUtil {

	/**
	 * 按枚举返回错误响应结果
	 * 
	 * @param orderUnknown
	 * @return
	 */
	public static Object fail(WxResponseCode responseCode) {
		return fail(responseCode.code(), responseCode.desc());
	}
}
