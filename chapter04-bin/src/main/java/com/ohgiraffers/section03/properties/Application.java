package com.ohgiraffers.section03.properties;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ContextConfiguration.class);
        Product carpBread = context.getBean("carpBread", Bread.class);
        System.out.println(carpBread);  //carpBread 1000 Wed Jun 04 13:18:28 KST 2025

        Product milk = context.getBean("milk", Beverage.class);
        System.out.println(milk); // bananamilk 2500 300

        Product water = context.getBean("water", Beverage.class);
        System.out.println(water); // cleanwater 3000 500

    }
}
