package com.haust.common.util;

import com.haust.common.type.WxResponseCode;

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
	 * @param responseCode
	 * @return
	 */
	public static Object fail(WxResponseCode responseCode) {
		return fail(responseCode.code(), responseCode.desc());
	}
}
