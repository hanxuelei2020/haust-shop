package com.haust.service.service.user;

import com.haust.service.domain.user.DtsComment;

import java.util.List;

public interface DtsCommentService {
    public List<DtsComment> queryGoodsByGid(Integer id, int offset, int limit);
    public List<DtsComment> query(Byte type, Integer valueId, Integer showType, Integer offset, Integer limit);
    public int count(Byte type, Integer valueId, Integer showType);
    public int save(DtsComment comment);
    public List<DtsComment> querySelective(String userId, String valueId, Integer page, Integer size, String sort,
                                           String order);
    public void deleteById(Integer id);
    public String queryReply(Integer id);
    public DtsComment findById(Integer id);
    public List<DtsComment> queryBrandCommentSelective(List<Integer> brandIds, String userId, String valueId,
                                                       Integer page, Integer size, String sort, String order);

}
