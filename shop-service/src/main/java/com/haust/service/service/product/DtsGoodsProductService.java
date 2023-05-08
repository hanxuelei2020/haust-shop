package com.haust.service.service.product;

import com.haust.common.util.CategorySellAmts;
import com.haust.service.domain.order.CloseOrder;
import com.haust.service.domain.product.DtsGoodsProduct;

import java.util.List;

public interface DtsGoodsProductService {
    List<DtsGoodsProduct> queryByGid(Integer gid);
    
    DtsGoodsProduct findById(Integer id);
    
    void deleteById(Integer id);
    
    void add(DtsGoodsProduct goodsProduct);
    
    int count();
    
    void deleteByGid(Integer gid);
    
    int addStock(Integer id, Short num);
    
    int addBrowse(Integer id, Short num);
    
    int reduceStock(Integer id, Integer goodsId, Short num);

    List<CategorySellAmts> categorySell(List<CloseOrder> closeOrders);
}