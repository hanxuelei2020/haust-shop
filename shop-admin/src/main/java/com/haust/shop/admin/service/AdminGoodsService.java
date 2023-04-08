package com.haust.shop.admin.service;

import com.haust.service.domain.product.DtsGoods;
import com.haust.shop.admin.domain.GoodsAllinone;

import java.util.List;

public interface AdminGoodsService {
    public Object list(String goodsSn, String name, Integer page, Integer limit, String sort, String order, List<Integer> brandIds);
    public Object update(GoodsAllinone goodsAllinone);
    public Object delete(DtsGoods goods);
    public Object create(GoodsAllinone goodsAllinone);
    public Object catAndBrand();
    public Object detail(Integer id);

}
