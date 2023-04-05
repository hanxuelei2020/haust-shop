package com.haust.service.service.product;

import com.haust.service.domain.product.DtsGoodsAttribute;

import java.util.List;

public interface DtsGoodsAttributeService {
    List<DtsGoodsAttribute> queryByGid(Integer goodsId);

    void add(DtsGoodsAttribute goodsAttribute);

    DtsGoodsAttribute findById(Integer id);

    void deleteByGid(Integer gid);
}