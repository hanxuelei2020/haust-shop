package com.haust.service.service.admin;

import com.haust.service.domain.admin.DtsFeedback;

import java.util.List;

public interface DtsFeedbackService {

    Integer add(DtsFeedback feedback);

    List<DtsFeedback> querySelective(Integer userId, String username, Integer page, Integer limit, String sort, String order);
}