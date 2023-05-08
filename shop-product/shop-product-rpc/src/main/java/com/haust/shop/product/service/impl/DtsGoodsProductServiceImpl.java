package com.haust.shop.product.service.impl;

import com.haust.common.util.CategorySellAmts;
import com.haust.service.domain.order.CloseOrder;
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
import java.time.LocalDateTime;
import java.util.List;

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
		return statMapper.categorySellStatis(closeOrders);
	}
}