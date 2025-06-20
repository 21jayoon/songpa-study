package com.ohgiraffers.associationmapping.section01.manytoone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentAccessException;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ManyToOneServiceTests {
    @Autowired
    private ManyToOneService manyToOneService; //field injection

    @DisplayName("Test N:1 연관관계 조회")
    @Test
    void manyToOneFindTest(){
        //given
        int menuCode = 10;

        //when
        Menu foundMenu = manyToOneService.findMenu(menuCode);

        //then
        Category category = foundMenu.getCategory();
        assertNotNull(category);
    }

    @DisplayName("Test N:1 연관관계 JPQL(객체 지향 쿼리)을 이용한 조회")
    @Test
    void manyToOneJpqlFindTest(){
        //given
        int menuCode = 10;

        // when
        String  categoryName = manyToOneService.findCategoryNameByJpql(menuCode);

        // then
        assertNotNull(categoryName);
        System.out.println("CategoryName : "+ categoryName);
    }

    private static Stream<Arguments> getMenuInfo(){
        return Stream.of(
                Arguments.of(124, "meatball bibimbap", 15000, 13, "퓨전분식", "Y")
        );
    }

    @DisplayName("Test N:1 연관관계 객체 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getMenuInfo")
    void manyToOneInsertTest(int menuCode, String menuName, int menuPrice,
                             int categoryCode, String categoryName,
                             String orderableStatus){
        //given
        //입력 받은 걸 DTO로 만들어주는 과정이 필요함
        MenuDTO menuInfo =  new MenuDTO(
                menuCode,
                menuName,
                menuPrice,
                new CategoryDTO(
                        categoryCode,
                        categoryName,
                        null
                ),
                orderableStatus
        );

        //when


        //then
        //registMenu(menuInfo)가 예외 던지지 않는지 확인
        assertDoesNotThrow(
                () -> manyToOneService.registMenu(menuInfo)
        );
    }
}