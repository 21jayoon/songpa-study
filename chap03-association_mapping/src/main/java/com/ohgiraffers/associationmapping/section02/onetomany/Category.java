package com.ohgiraffers.associationmapping.section02.onetomany;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "category_and_menu")
@Table(name = "tbl_category")
public class Category {
    @Id
    private int categoryCode;

    private String  categoryName;
    private Integer refCategoryCode;

    /*즉시 로딩 or 지연 로딩
    * 자동으로 (알아서) JOIN해서 갖고 오거나 필요할 때 판단해서 갖고 오거나.
    * @OneToMany에서 Default = 지연로딩이지만 필요에 따라 즉시 로딩으로 변경 가능*/

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    // 중요!! cascade = CascadeType.PERSIST 넣기
    // :그래야 menu에 insert할 때 join column인 categoryCode도 같이 삽입됨
    @JoinColumn(name = "categoryCode")
    //@JoinColumn해줄 때 두 tbl이 각 key 이름이 다르다면
    //PK에 해당하는 key속성의 이름 말고
    //***FK에 해당하는 key 속성의 이름***을 적어줘야한다.
    private List<Menu> menuList;

    public Category(){}

    public Category(int categoryCode, String categoryName, Integer refCategoryCode, List<Menu> menuList) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
        this.menuList = menuList;
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

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                ", menuList=" + menuList +
                '}';
    }
}
