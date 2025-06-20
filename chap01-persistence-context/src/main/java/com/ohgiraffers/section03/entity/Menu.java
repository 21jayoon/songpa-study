package com.ohgiraffers.section03.entity;

import jakarta.persistence.*;

//이름 지어주는 이유?: 중복 방지(동명이entity 안 됨), 만약 name 속성 따로 주지 않으면 메소드 이름이 entity 이름이 됨
@Entity(name = "Section03Menu")
@Table(name = "tbl_menu")
//@Table : 어떤 테이블과 연동할 것인가?
public class Menu {
    // DTO 데이터를 전송하기 위한 Transfer 객체

    //PK를 지정해줘야 method 이름 아래에 붙은 빨간 줄 지워짐.
    // @Id 어노테이션을 꼭 붙이도록.(그래야 private 필드를 PK로 인식함)
    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    // 자동 매핑도 가능하지만 아직 초반이므로 명시적으로 매핑해주도록 하겠음
    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    //생성자 하나 생성. protected = 외부에서 new Menu로 생성되게 하지 않겠다
    protected Menu(){}

    //그러나 테스트를 위해서 public 생성자를 따로 하나 생성한다. (원래는 public 생성자 따로 작성 권장하지 않음)
    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }
    //"PK이자 AUTO_INCREMENT"인 int menuCode는 없앤다

    //getter setter도 생성
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

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
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
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
