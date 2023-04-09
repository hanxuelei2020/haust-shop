package com.haust.service.service.user;

public interface WxAgencyService {
    public String createAgencyShareUrl(Integer userId, Integer type, Integer shareObjId);
    public String getShareObjUrl(Integer type, Integer shareObjId);
    public String createShareUrl(Integer type, Integer shareObjId);

}
