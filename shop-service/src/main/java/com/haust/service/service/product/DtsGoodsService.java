package com.haust.service.service.product;

import com.haust.service.domain.product.DtsGoods;

import java.util.List;

public interface DtsGoodsService {
    public void add(DtsGoods goods);

    List<DtsGoods> queryByHot(int offset, int limit);

    List<DtsGoods> queryByNew(int offset, int limit);

    List<DtsGoods> queryByCategory(List<Integer> catList, int offset, int limit);

    List<DtsGoods> queryByCategory(Integer catId, int offset, int limit);

    List<DtsGoods> querySelective(Integer catId, Integer brandId, String keywords, Boolean isHot, Boolean isNew,
                                  Integer offset, Integer limit, String sort, String order);
    public List<DtsGoods> querySelective(String goodsSn, String name, Integer page, Integer size, String sort,
                                         String order,List<Integer> brandIds);
    public DtsGoods findById(Integer id);
    public DtsGoods findByGoodsSn(String goodsSn);
    public DtsGoods findByIdVO(Integer id);
    public DtsGoods findBySnVO(String sn);
    public Integer queryOnSale();
    public int updateById(DtsGoods goods);
    public void deleteById(Integer id);
    public int count();
    public boolean checkExistByName(String name);
    public List<DtsGoods> queryByBrandId(int bid, int cid, int offset, int limit);
    public List<DtsGoods> queryByCategoryAndNotSameBrandId(int bid, int cid, int offset, int limit);

}