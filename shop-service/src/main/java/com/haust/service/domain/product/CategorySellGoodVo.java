package com.haust.service.domain.product;

import java.io.Serializable;

public class CategorySellGoodVo implements Serializable {
    private String categoryName;
    private String pid;
    private String id;

    public CategorySellGoodVo() {
    }

    public CategorySellGoodVo(String categoryName, String pid, String id) {
        this.categoryName = categoryName;
        this.pid = pid;
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
