package com.ohgiraffers.jpql.section03.projection;

import jakarta.persistence.Entity;

public class CategoryInfo {
    private int categoryCode;
    private String categoryName;
    
    public CategoryInfo(){}

    public CategoryInfo(int categoryCode, String categoryName) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    } //매개변수 있는 생성자 작성

    public int getcategoryCode() {
        return categoryCode;
    }

    public void setcategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
