package com.haust.service.service.search;

import com.haust.service.domain.search.DtsSearchHistory;

import java.util.List;

public interface DtsSearchHistoryService {
    void save(DtsSearchHistory searchHistory);
    List<DtsSearchHistory> queryByUid(int uid);
    void deleteByUid(int uid);
    List<DtsSearchHistory> querySelective(String userId, String keyword, Integer page, Integer size, String sort, String order);
}