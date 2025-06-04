package com.ohgiraffers.section01.scope.subsection01.singleton;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import com.ohgiraffers.common.ShoppingCart;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
     /* what is bean scope?
     *Spring 에서 bean이 생성될 때
     * 생성되는 인스턴스의 범위를 의미한다.
     * 스프링에서는 다양한 Bean scope를 제공한다.
     * 1. singleton : 하나의 인스턴스만을을 생성하고, 모든 bean이 해당 인스턴스를 공유하며 사용
     * 2. prototype : 매법 인스턴스를 새롭게 생성한다
     * 3. request : HTTP 요청을 처리할 떄마다 새로운 인스턴스를 생성하고 요청처리가 끝나면 인스턴스를 폐기한다.
     * 4. session : HTTP session 당 하나의 인스턴스를 생성하고, 세션이 종료되면 인스턴스를 폐기한다.
     * 5. globalSession : =전역 세션. 전역 세션 당 하나의 인스턴스를 생성하고, 전역 세션이 종료되면 인스턴스를 폐기한다. */

        ApplicationContext context =
                    new AnnotationConfigApplicationContext(ContextConfiguration.class);
//        String[] beanNames = context.getBeanDefinitionNames();
//        for(String beanName : beanNames) {
//            System.out.println(beanName);
//        }
        /*contextConfiguration
        carpBread
        milk
        water
        cart*/

        /*1. sigleton 사용*/
        //1) 붕어빵, 초코 우유, 물 등의 Bean 객체를 반환받는다
        Product carpBread = context.getBean("carpBread", Bread.class);
        Product milk = context.getBean("milk", Beverage.class);
        Product water = context.getBean("water", Beverage.class);

        //2)첫번째 손님이 쇼핑 카트를 꺼낸다
        ShoppingCart cart1 = context.getBean("cart", ShoppingCart.class);
        cart1.addItem(carpBread);
        cart1.addItem(milk);
        System.out.println("cart1에 담긴 상품 = "+cart1.getItem()); //cart1에 담긴 상품 = [붕어빵 1000 Wed Jun 04 11:35:29 KST 2025, CHOCO_MILK 2500 300]

        //3)두번째 손님이 쇼핑 카트를 꺼낸다
        ShoppingCart cart2 = context.getBean("cart", ShoppingCart.class);
        cart2.addItem(water);
        System.out.println("cart2에 담긴 상품 = "+ cart2.getItem());
        //cart2에 담긴 상품 = [붕어빵 1000 Wed Jun 04 11:36:49 KST 2025, CHOCO_MILK 2500 300, WATER 1000 500]
        //cart1과 2과 다른 cart로 여겨진 것이 아니라 singleton이라서 같은 cart로 인식됨
        System.out.println(cart1.hashCode()); //1047087935
        System.out.println(cart2.hashCode()); //1047087935
        //hashCode를 확인해봐도 두 카트가 같은 걸로 인식되고 있음을 볼 수 있음

        /*Spring Frameworkd에서 Bean의 기본 스코프는 singleton이다.
        * singleton scope를 갖는 Bean 어플리케이션 내에서 유일한 인스턴스를 갖는다.
        * 위 예제에서 손님 두 명이 각각 쇼핑 카트를 이용해 상품을 담는 상황을 가정했지만
        * singleton으로 관리되는 cart는 두 손님이 동일 카트에 물건을 담게 된다.*/
    }
}
