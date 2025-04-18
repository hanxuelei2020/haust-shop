package com.haust.shop.core.express;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haust.shop.core.express.config.ExpressProperties;
import com.haust.shop.core.express.dao.ExpressInfo;
import com.haust.shop.core.util.HttpUtil;

/**
 * 物流查询服务
 *
 * 快递鸟即时查询API http://www.kdniao.com/api-track
 */
public class ExpressService {
	// 请求url
	private String ReqURL = "http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";

	private ExpressProperties properties;

	public ExpressProperties getProperties() {
		return properties;
	}

	public void setProperties(ExpressProperties properties) {
		this.properties = properties;
	}
	
	/**
	 * 获取所有物流供应商信息
	 *
	 * @param vendorCode
	 * @return
	 */
	public List<Map<String, String>> getAllVendor() {
		return properties.getVendors();
	}

	/**
	 * 获取物流供应商名
	 *
	 * @param vendorCode
	 * @return
	 */
	public String getVendorName(String vendorCode) {
		for (Map<String, String> item : properties.getVendors()) {
			if (item.get("code").equals(vendorCode))
				return item.get("name");
		}
		return null;
	}

	/**
	 * 获取物流信息
	 *
	 * @param expCode
	 * @param expNo
	 * @return
	 */
	public ExpressInfo getExpressInfo(String expCode, String expNo) {
		try {
			String result = getOrderTracesByJson(expCode, expNo);
			ObjectMapper objMap = new ObjectMapper();
			ExpressInfo ei = objMap.readValue(result, ExpressInfo.class);
			// 对物流按时间进行倒序排列
			if (ei != null && ei.getTraces() != null && ei.getTraces().size() > 0) {
				ei.getTraces().sort((m1, m2) -> m2.getAcceptTime().compareTo(m1.getAcceptTime()));
			}
			ei.setShipperName(getVendorName(expCode));
			return ei;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Json方式 查询订单物流轨迹
	 *
	 * @throws Exception
	 */
	private String getOrderTracesByJson(String expCode, String expNo) throws Exception {
		if (!properties.isEnable()) {
			return null;
		}

		String requestData = "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("RequestData", URLEncoder.encode(requestData, "UTF-8"));
		params.put("EBusinessID", properties.getAppId());
		params.put("RequestType", "1002");
		String dataSign = encrypt(requestData, properties.getAppKey(), "UTF-8");
		params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
		params.put("DataType", "2");

		String result = HttpUtil.sendPost(ReqURL, params);

		// 根据公司业务处理返回的信息......

		return result;
	}

	/**
	 * MD5加密
	 *
	 * @param str
	 *            内容
	 * @param charset
	 *            编码方式
	 * @throws Exception
	 */
	private String MD5(String str, String charset) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes(charset));
		byte[] result = md.digest();
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < result.length; i++) {
			int val = result[i] & 0xff;
			if (val <= 0xf) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(val));
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * Sign签名生成
	 *
	 * @param content
	 *            内容
	 * @param keyValue
	 *            Appkey
	 * @param charset
	 *            编码方式
	 * @return DataSign签名
	 */
	private String encrypt(String content, String keyValue, String charset) {
		if (keyValue != null) {
			content = content + keyValue;
		}
		byte[] src = new byte[0];
		try {
			src = MD5(content, charset).getBytes(charset);
			return Base64Utils.encodeToString(src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
