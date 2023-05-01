package com.haust.service.domain.order;

import java.io.Serializable;

public class SharedUserVo implements Serializable {
    private Integer shardUserId;
    private String startTime;
    private String endTime;

    public SharedUserVo() {
    }

    public SharedUserVo(Integer shardUserId, String startTime, String endTime) {
        this.shardUserId = shardUserId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getShardUserId() {
        return shardUserId;
    }

    public void setShardUserId(Integer shardUserId) {
        this.shardUserId = shardUserId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
