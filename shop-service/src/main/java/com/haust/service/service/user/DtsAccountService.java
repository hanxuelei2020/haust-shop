package com.haust.service.service.user;

import com.haust.service.domain.order.DtsOrder;
import com.haust.service.domain.user.DtsAccountTrace;
import com.haust.service.domain.user.DtsUser;
import com.haust.service.domain.user.DtsUserAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DtsAccountService {
    public DtsUserAccount findShareUserAccountByUserId(Integer shareUserId);
    public List<Integer> findAllSharedUserId();
    public Integer getShardLevelUserId(Integer shareUserId);
    public void setSettleMentAccount(Integer sharedUserId, Integer type, BigDecimal toSettleMoney, String startTime, String endTime) throws Exception;
    public void settleApplyTrace(Integer sharedUserId, String startTime, String endTime, Integer type, BigDecimal toSettleMoney, String mobile);
    public Map<String, Object> getStatistics(Integer sharedUserId, int dayAgo);
    public List<DtsAccountTrace> queryAccountTraceList(Integer userId, Integer page, Integer size);
    public void addExtractRecord(Integer userId, BigDecimal applyAmt, String mobile, String smsCode,
                                 BigDecimal hasAmount);
    public void add(DtsUserAccount userAccount);
    public List<DtsAccountTrace> getAccountTraceList(Integer userId, Byte... types);
    public List<DtsAccountTrace> querySelectiveTrace(List<DtsUser> userList, List<Byte> status, Integer page,
                                                     Integer size, String sort, String order);
    public boolean approveAccountTrace(Integer traceId, Byte status, String traceMsg);
    public boolean settlementPreviousAgency(Integer userId, BigDecimal settleMoney);

}
