package com.haust.service.service.user;

import com.haust.service.domain.user.DtsCollect;

import java.util.List;

public interface DtsCollectService {
    public int count(int uid, Integer gid);
    public List<DtsCollect> queryByType(Integer userId, Byte type, Integer page, Integer size);
    public int countByType(Integer userId, Byte type);
    public DtsCollect queryByTypeAndValue(Integer userId, Byte type, Integer valueId);
    public void deleteById(Integer id);
    public int add(DtsCollect collect);
    public List<DtsCollect> querySelective(String userId, String valueId, Integer page, Integer size, String sort,
                                           String order);

}
