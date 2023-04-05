package com.haust.service.service.product;

import com.haust.service.domain.product.DtsBrand;

import java.util.List;

public interface DtsBrandService {
    public List<DtsBrand> queryVO(int offset, int limit);
    public int queryTotalCount();
    public DtsBrand findById(Integer id);
    public List<DtsBrand> querySelective(String id, String name, Integer page, Integer size, String sort,
                                         String order);
    public int updateById(DtsBrand brand);
    public void deleteById(Integer id);
    public void add(DtsBrand brand);
    public List<DtsBrand> all();
    public List<DtsBrand> getAdminBrands(Integer adminId);
    public String getBrandCategory(Integer categoryId);
}
