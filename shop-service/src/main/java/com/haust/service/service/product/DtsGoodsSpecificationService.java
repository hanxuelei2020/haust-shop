package com.haust.service.service.product;

import com.haust.service.domain.product.DtsGoodsSpecification;

import java.util.List;

public interface DtsGoodsSpecificationService {

    List<DtsGoodsSpecification> queryByGid(Integer id);

    DtsGoodsSpecification findById(Integer id);

    void deleteByGid(Integer gid);

    void add(DtsGoodsSpecification goodsSpecification);

    Object getSpecificationVoList(Integer id);
}