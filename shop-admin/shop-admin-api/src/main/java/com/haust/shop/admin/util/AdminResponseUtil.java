package com.haust.shop.admin.util;


import com.haust.common.util.ResponseUtil;

/**
 * 管理后台接口枚举信息的响应
 * 
 * @author 韩雪磊
 * @since 1.0.0
 * @QQ 2407411399
 */
public class AdminResponseUtil extends ResponseUtil {

	/**
	 * 按枚举返回错误响应结果
	 * 
	 * @param orderUnknown
	 * @return
	 */
	public static Object fail(AdminResponseCode responseCode) {
		return ResponseUtil.fail(responseCode.code(), responseCode.desc());
	}
}
