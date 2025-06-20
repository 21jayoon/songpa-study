package com.ohgiraffers.jpql.section02.parameter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MenuBindingRepositoryTests {
    @Autowired
    private ParameterBindingRepository parameterBindingRepository;

    @DisplayName("이름 기준 파라미터 바인딩 테스트")
    @Test
    void testParameterBindingByName(){
        String menuName = "민트미역국";
        List<Menu> menuList = parameterBindingRepository.selectMenuByBindingName(menuName);
        System.out.println(menuList);
        assertEquals(menuName, menuList.get(0).getMenuName());
        // assertEquals(...) =
        // menuName "민트미역국"과 출력할 menuList의 1번째 인덱스(어차피 하나 출력하는 거긴 하지만)에서
        // 해당 인덱스의 이름이 같은지 확인한다
    }

    @DisplayName("위치 기준 파라미터 바인딩 테스트")
    @Test
    void testParameterBindingByPosition(){
        //given
        String menuName = "붕어빵초밥";
        //when
        List<Menu> menuList = parameterBindingRepository.selectMenuByBindingPosition(menuName);
        //then
        System.out.println(menuList);
        assertEquals(menuName, menuList.get(0).getMenuName());
    }
}
