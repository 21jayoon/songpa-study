package com.ohgiraffers.common;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Product> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product item) {
        items.add(item);
        //items라는 List에 parameter item값을 add한다
    }

    public List<Product> getItem() {
        return items;
    }
}
