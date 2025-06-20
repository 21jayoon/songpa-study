package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.*;

@Entity(name="menu_and_category")
@Table(name="tbl_menu")
public class Menu {
    @Id
    private int menuCode;
    private String menuName;
    private int menuPrice;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    // .PERSIST : 영속성 전이
    // (cascade = CascadeType.PERSIST) 이걸 넣어야
    // "category도 같이 영속화할게요(=카테고리도 같이 추가할게요)"가 됨.

    /* 영속성 전이
     * 특정 엔티티를 영속화 할 때 연관 된 엔티티도 함께 영속화 한다는 의미이다.*/
    /* 기본적으로는 즉시 로딩되지만 필요에 따라 지연 로딩으로 변경할 수 있다. */

    // FetchType.EAGER = 즉시 로딩
    // FetchType.LAZY = 지연 로딩
    // @ManyToOne일 때는 EAGER 즉시 로딩이 Default
    @JoinColumn(name = "categoryCode") //@JoinColumn의 이름은 FK 이름으로 적어야 한다
    private Category category;
    //연관관계 매핑을 보고 싶기 때문에 categoryCode라고 안 적고 category라고 적은 후
    //Category entity class에서 연관관계 매핑 사용하는 방법을 알아본다.

    private String orderableStatus;

    public Menu(){}

    public Menu(int menuCode, String menuName, int menuPrice, Category category, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", category=" + category +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
