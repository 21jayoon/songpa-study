package com.ohgiraffers.associationmapping.section03.bidirection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BiDirectionServiceTests {
    @Autowired
    private BiDirectionService biDirectionService;

    @DisplayName("양방향 연관관계 매핑 조회(연관관계의 주인)")
    @Test
    void biDirectionFindTest1(){
        //given
        int menuCode = 11;

        //when
        Menu foundMenu = biDirectionService.findMenu(menuCode);

        //then
        assertEquals(menuCode, foundMenu.getMenuCode());
    }

    @DisplayName("양방향 연관관계 매핑 조회(연관관계 주인 아닌 경우)")
    @Test
    void biDirectionFindTest2(){
        //given
        int categoryCode = 10;

        //when
        Category foundCategory = biDirectionService.findCategory(categoryCode);

        //then
        assertEquals(categoryCode, foundCategory.getCategoryCode());
    }
    // Using OneToMany or ManyToOne instead using BiDirection.
    // BiDirection is not recommended
}
