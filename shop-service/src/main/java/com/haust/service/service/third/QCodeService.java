package com.haust.service.service.third;

import com.haust.service.domain.order.DtsGroupon;
import java.math.BigDecimal;

/**
 * 这里使用了缓存，目的是将商城之类的东西缓存到腾讯云或者阿里云
 */
public interface QCodeService {


	public String createGrouponShareImage(String goodName, String goodPicUrl, DtsGroupon groupon, BigDecimal counterPrice, BigDecimal retailPrice);

	/**
	 * 创建商品分享图
	 * 
	 * @param shareUserId
	 * @param goodId
	 * @param goodPicUrl
	 * @param goodName
	 */
	public String createGoodShareImage(Integer shareUserId,String goodId, String goodPicUrl, String goodName,BigDecimal counterPrice,BigDecimal retailPrice);

	/**
	 * 根据用户创建用户的分享二维码
	 * @param userId
	 * @return
	 */
	public String createShareUserImage(Integer userId);



	

	public String createShareTopicImage(Integer topicId, String picUrl, String subtitle, BigDecimal price);


	/**
	 * 创建商品的分享海报
	 * @param shareUserId
	 * @param brandId
	 * @param picUrl
	 * @param name
	 * @param defaultCategory
	 * @return
	 */
	public String createBrandImage(Integer shareUserId,Integer brandId, String picUrl, String name, String defaultCategory);
}
