package com.haust.service.service.dict;

import com.haust.service.domain.dict.DtsStorage;

import java.util.List;

public interface DtsStorageService {

    void deleteByKey(String key);

    void add(DtsStorage storageInfo);

    DtsStorage findByKey(String key);

    int update(DtsStorage storageInfo);

    DtsStorage findById(Integer id);

    List<DtsStorage> querySelective(String key, String name, Integer page, Integer limit, String sort,
                                    String order);
}