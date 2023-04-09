package com.haust.service.service.user;

import com.haust.service.domain.user.DtsAddress;

import java.util.List;

public interface DtsAddressService {
    public DtsAddress findById(Integer id);
    public int add(DtsAddress address);
    public int update(DtsAddress address);
    public void delete(Integer id);
    public void resetDefault(Integer userId);
    public List<DtsAddress> querySelective(Integer userId, String name, Integer page, Integer limit, String sort,
                                           String order);

    List<DtsAddress> queryByUid(Integer userId);

    DtsAddress findDefault(Integer userId);
}
