package com.haust.service.service.user;

import com.haust.common.util.DayStatis;
import com.haust.service.domain.user.DtsUser;
import com.haust.service.domain.user.DtsUserAccount;
import com.haust.service.domain.user.UserVo;

import java.util.List;
import java.util.Map;

public interface DtsUserService {

    DtsUser findById(Integer userId);

    UserVo findUserVoById(Integer userId);

    DtsUser queryByOid(String openId);

    void add(DtsUser user);

    int updateById(DtsUser user);

    List<DtsUser> querySelective(String username, String mobile, Integer page, Integer size, String sort,
                                 String order);

    int count();

    List<DtsUser> queryByUsername(String username);

    boolean checkByUsername(String username);

    List<DtsUser> queryByMobile(String mobile);

    List<DtsUser> queryByOpenid(String openid);

    void deleteById(Integer id);

    void approveAgency(Integer userId,Integer settlementRate,String shareUrl);
    public DtsUserAccount detailApproveByUserId(Integer userId);
    public List<DtsUser> queryDtsUserListByNickname(String username,String mobile);
    public List<DayStatis> recentCount(int statisDaysRang);

    public List<Map> statUser();
}