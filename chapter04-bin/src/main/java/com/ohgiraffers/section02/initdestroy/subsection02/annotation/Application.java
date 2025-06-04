package com.ohgiraffers.section02.initdestroy.subsection02.annotation;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import com.ohgiraffers.common.ShoppingCart;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        Product carpBread = context.getBean("carpBread", Bread.class);
        Product milk = context.getBean("milk", Beverage.class);
        Product water = context.getBean("water", Beverage.class);

        //2)첫번째 손님이 쇼핑 카트를 꺼낸다
        ShoppingCart cart1 = context.getBean("cart", ShoppingCart.class);
        cart1.addItem(carpBread);
        cart1.addItem(milk);
        System.out.println("cart1에 담긴 상품 = "+cart1.getItem());
        //cart1에 담긴 상품 = [붕어빵 1000 Wed Jun 04 11:44:59 KST 2025, CHOCO_MILK 2500 300]

        //3)두번째 손님이 쇼핑 카트를 꺼낸다
        ShoppingCart cart2 = context.getBean("cart", ShoppingCart.class);
        cart2.addItem(water);
        System.out.println("cart2에 담긴 상품 = "+ cart2.getItem()); //cart2에 담긴 상품 = [WATER 1000 500]

        //컨테이너가 종료되지 않았을 수도 있기 때문에 컨테이너 강제로 파괴(강제 종료)
        ((AnnotationConfigApplicationContext) context).close();
        /*init 메소드는 bean 객체 생성 시점에 동작하므로 바로 확인할 수 있지만 
        destroy method는 bean 객체 소멸 시점에 동작하므로 컨테이너가 종료되지 않을 경우 확인 불가.
        가비지 컬렉터가 해당 bean을 메모리에서 지울 때 destroy method가 동작하게 되는데,
        메모리에서 지우기 전에 프로세스가 종료되기 때문이다.
        따라서 위와 같이 강제로 컨테이너를 종료시키면 destroy method가 동작한다.*/

        /*execute output :
        * The owner opened the store. Now you can go shopping
        * cart1에 담긴 상품 = [붕어빵 1000 Wed Jun 04 13:06:09 KST 2025, CHOCO_MILK 2500 300]
        * cart2에 담긴 상품 = [WATER 1000 500]
        * The owner closed the store. See you tomorrow */
    }
}
