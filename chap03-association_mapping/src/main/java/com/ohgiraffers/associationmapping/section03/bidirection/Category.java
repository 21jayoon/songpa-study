package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "bidirection_category")
// 250620 9:31 entity name이 구분될 수 있도록 바꾸어줌
@Table(name = "tbl_category")
public class Category {
    //category table과 매핑할 entity class

    // write a field
    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")
    // @OneToMany의 기본 fetch 타입은 LAZY다.
    private List<Menu> menuList;
    // I noted in menu.java that "Real" relation is the entity that have a FK.
    // so the fake relation, category entity needs an annotation @OneToMay
    // with (mappedBy = ).
    // Also, the mapped value must be tha FK of the entity which has a "Real" relationship
    // Referred by    private Category category;   from the menu entity,
    // (mappedBy = "category") is the right answer.

    public Category(){}

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRefCategoryCode() {
        return refCategoryCode;
    }

    public void setRefCategoryCode(Integer refCategoryCode) {
        this.refCategoryCode = refCategoryCode;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }
    public void setMenuList(List<Menu> menuList){
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
