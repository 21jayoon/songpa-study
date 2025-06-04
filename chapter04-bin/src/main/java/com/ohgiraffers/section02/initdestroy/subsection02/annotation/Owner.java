package com.ohgiraffers.section02.initdestroy.subsection02.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
// build.gradle에 implementation("javax.annotation:javax.annotation-api:1.3.2") 추가해줘서 javax 사용가능

@Component
public class Owner {
    /*init method와 같은 기능을 가진 설정 어노테이션*/
    @PostConstruct
    public void openShop() {
        System.out.println("The owner opened the store. Now you can go shopping");
    }

    /*destroy-method와 같은 설정 어노테이션*/
    @PreDestroy
    public void closeShop() {
        System.out.println("The owner closed the store. See you tomorrow");
    }
}
