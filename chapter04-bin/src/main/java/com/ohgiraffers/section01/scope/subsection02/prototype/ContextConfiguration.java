package com.ohgiraffers.section01.scope.subsection02.prototype;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import com.ohgiraffers.common.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
//위 어노테이션이 붙은 해당 클래스가 설정 파일임을 알려줌
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
}
