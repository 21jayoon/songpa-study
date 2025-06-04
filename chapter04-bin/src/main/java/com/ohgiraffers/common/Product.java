package com.ohgiraffers.common;

public abstract class Product {
    private String name;
    private int price;

    public Product(){}

    //모든 필드 초기화하는 생성자
    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " "+ price;
    }
}
