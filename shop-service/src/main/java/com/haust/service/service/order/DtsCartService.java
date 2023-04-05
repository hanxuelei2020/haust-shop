package com.haust.service.service.order;

import com.haust.service.domain.order.DtsCart;

import java.util.List;

public interface DtsCartService {
    public DtsCart queryExist(Integer goodsId, Integer productId, Integer userId);
    public void add(DtsCart cart);
    public int updateById(DtsCart cart);
    public List<DtsCart> queryByUid(int userId);
    public List<DtsCart> queryByUidAndChecked(Integer userId);
    public int delete(List<Integer> productIdList, int userId);
    public DtsCart findById(Integer id);
    public int updateCheck(Integer userId, List<Integer> idsList, Boolean checked);
    public void clearGoods(Integer userId);
    public List<DtsCart> querySelective(Integer userId, Integer goodsId, Integer page, Integer limit, String sort,
                                        String order);
    public void deleteById(Integer id);
    public boolean checkExist(Integer goodsId);
}
