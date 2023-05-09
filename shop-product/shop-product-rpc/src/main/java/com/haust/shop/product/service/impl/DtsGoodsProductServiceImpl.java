package com.haust.shop.product.service.impl;

import com.haust.common.util.CategorySellAmts;
import com.haust.service.domain.order.CloseOrder;
import com.haust.service.domain.product.CategorySellGoodVo;
import com.haust.service.domain.product.DtsGoodsProduct;
import com.haust.service.domain.product.DtsGoodsProductExample;
import com.haust.service.service.product.DtsGoodsProductService;
import com.haust.shop.product.mapper.DtsGoodsProductMapper;
import com.haust.shop.product.mapper.GoodsProductMapper;
import com.haust.shop.product.mapper.StatMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DubboService
public class DtsGoodsProductServiceImpl implements DtsGoodsProductService {
	@Autowired
	private DtsGoodsProductMapper dtsGoodsProductMapper;
	@Autowired
	private GoodsProductMapper goodsProductMapper;

	@Autowired
	private StatMapper statMapper;

	public List<DtsGoodsProduct> queryByGid(Integer gid) {
		DtsGoodsProductExample example = new DtsGoodsProductExample();
		example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
		return dtsGoodsProductMapper.selectByExample(example);
	}

	public DtsGoodsProduct findById(Integer id) {
		return dtsGoodsProductMapper.selectByPrimaryKey(id);
	}

	public void deleteById(Integer id) {
		dtsGoodsProductMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(DtsGoodsProduct goodsProduct) {
		goodsProduct.setAddTime(LocalDateTime.now());
		goodsProduct.setUpdateTime(LocalDateTime.now());
		dtsGoodsProductMapper.insertSelective(goodsProduct);
	}

	public int count() {
		DtsGoodsProductExample example = new DtsGoodsProductExample();
		example.or().andDeletedEqualTo(false);
		return (int) dtsGoodsProductMapper.countByExample(example);
	}

	public void deleteByGid(Integer gid) {
		DtsGoodsProductExample example = new DtsGoodsProductExample();
		example.or().andGoodsIdEqualTo(gid);
		dtsGoodsProductMapper.logicalDeleteByExample(example);
	}

	public int addStock(Integer id, Short num) {
		return goodsProductMapper.addStock(id, num);
	}

	public int addBrowse(Integer id, Short num) {
		return goodsProductMapper.addBrowse(id, num);// 新增商品流量量
	}

	public int reduceStock(Integer id, Integer goodsId, Short num) {
		goodsProductMapper.addSales(goodsId, num);// 每次需将商品的销售量加下
		return goodsProductMapper.reduceStock(id, num);
	}

	@Override
	public List<CategorySellAmts> categorySell(List<CloseOrder> closeOrders) {
		// 将 CloseOrder 转换为 Map<goodsId, price>
		Map<String, BigDecimal> closeOrderMap = closeOrders.stream()
				.collect(Collectors.toMap(
						CloseOrder::getGoodsId,
						CloseOrder::getPrice
				));
		List<String> goodIds = closeOrders.stream().map(CloseOrder::getGoodsId).distinct().collect(Collectors.toList());
		List<CategorySellGoodVo> vos = statMapper.categorySellStatis(goodIds);
		Map<String, BigDecimal> categorySellAmtsMap = vos.stream()
				.collect(Collectors.groupingBy(
						CategorySellGoodVo::getCategoryName, // 按照一级分类进行分组
						Collectors.mapping(
								g -> closeOrderMap.get(g.getId()), // 获取 price
								Collectors.reducing(BigDecimal.ZERO, BigDecimal::add) // 对 price 求和
						)
				));
		// 将map转化为结果
		List<CategorySellAmts> res = new ArrayList<>();
		categorySellAmtsMap.forEach((k, v) -> {
			CategorySellAmts amts = new CategorySellAmts();
			amts.setName(k);
			amts.setValue(v);
			res.add(amts);
		});
		return res;
	}
}