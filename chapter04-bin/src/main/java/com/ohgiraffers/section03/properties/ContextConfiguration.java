package com.ohgiraffers.section03.properties;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.print.DocFlavor;

@Configuration
@PropertySource("section03/properties/product-info.properties")
public class ContextConfiguration {
    /*치환자 문법${}을 이용해 properties에 저장된 key를 입력하면 value에 해당하는 값을 꺼내온다
    * 공백 사용 시 값을 읽어오지 못하니 주의한다.
    * : colon을 사용하면 값을 읽어오지 못할 때 사용할 대체 값을 작성할 수 있다. */

    //@Value("${}") =
    // ${bread.carpbread.name} 이 키 값을 읽어와서 해당 키의 value를 아래 String carpBreadName;필드에 넣어줄거다
    @Value("${bread.carpbread.name}")
    private String carpBreadName;

    @Value("${bread.carpbread.price:0}") // bread.carp.bread.price 값이 없다면 0으로 치환하겠다
    // int값인 경우 오류가 나기 쉬운데(?) 그를 대비할 수 있따.
    private int carpBreadPrice;


    @Value("${beverage.milk.name}")
    private String milkName;

    @Value("${beverage.milk.price}")
    private int milkPrice;

    @Value("${beverage.milk.capacity:0}")
    private int milkCapacity;


    @Bean
    public Product carpBread(){
        return new Bread(carpBreadName, carpBreadPrice, new java.util.Date());
    }

    @Bean
    public Product milk(){
        return new Beverage(milkName, milkPrice, milkCapacity);
    }

    //@Value는 매개변수 안에 들어갈 수도 있다.
    @Bean
    public Product water(@Value("${beverage.water.name}") String waterName,
                         @Value("${beverage.water.price}") int waterPrice,
                         @Value("${beverage.water.capacity}") int waterCapacity){
        return new Beverage(waterName, waterPrice, waterCapacity);
    }
}
