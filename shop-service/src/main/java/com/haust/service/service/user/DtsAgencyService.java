package com.haust.service.service.user;

public interface DtsAgencyService {
    public String getDtsAgencyShare(Integer userId, Integer type, Integer shareObjId);
    public void saveDtsAgencyShare(Integer userId, Integer type, Integer shareObjId, String shareUrl);
}
