package com.ohgiraffers.section02.initdestroy.subsection01.java;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import com.ohgiraffers.common.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ContextConfiguration {
    @Bean
    public Product carpBread(){
        return new Bread("붕어빵", 1000, new java.util.Date());
    }

    @Bean
    public Product milk(){
        return new Beverage("CHOCO_MILK", 2500, 300);
    }

    @Bean
    public Product water(){
        return new Beverage("WATER", 1000, 500);
    }

    @Bean
    @Scope("prototype")
    //IoC Container에서 scope의 기본 값은 singleton이다.
    public ShoppingCart cart(){
        return new ShoppingCart();
    }

    @Bean(initMethod = "openShop", destroyMethod = "closeShop")
    //openShop이라는 메소드를 불러와서 초기화하고 closhShop 메소드를 불러와서 Bean(?)을 소멸시킨다.
    public Owner owner(){
        return new Owner();
    }
}
