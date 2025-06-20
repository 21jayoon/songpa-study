package com.ohgiraffers.jpql.section01.simple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SimpleJPQlRepositoryTests {

    //autowired로 필드주입
    @Autowired
    private SimpleJPQLRepository simpleJPQlRepository;

    @DisplayName("TypedQuery를 이용한 단일행, 단일컬럼 조회 테스트")
    @Test
    void testSelectSingleMenuByTypedQuery() {
        String menuName = simpleJPQlRepository.selectSingleMenuByTypedQuery();
        assertEquals("한우딸기국밥", menuName);
    }

    @DisplayName("TypedQuery를 이용한 다중행 조회 테스트")
    @Test
    public void testSelectResultListByTypedQuery(){
        List<Menu> menuList = simpleJPQlRepository.selectMultiRowByTypedQuery();
        // 행 하나 값이 아닌 여러 행으로 결과가 출력될 것이기 떄문에 .selectMultiRowByTypedQuery()를 사용한다
        System.out.println(menuList);
        assertNotNull(menuList);
    }

    @DisplayName("DISTINCT 연산자 사용 조회 테스트")
    @Test
    public void testSelectUsingDistinct(){
        List<Integer> categoryList = simpleJPQlRepository.selectUsingDistinct();
        System.out.println(categoryList);
        assertNotNull(categoryList);
    }

    @DisplayName("Test categoryCode 11, 12 is exeucte well")
    @Test
    public void testSelectUsingIn(){
        List<Menu> specificCategoryList = simpleJPQlRepository.selectUsingIn();
        System.out.println(specificCategoryList);
        assertNotNull(specificCategoryList);
    }

    @DisplayName("Test the list print include '마늘' in menu name")
    @Test
    public void testSelectUsingLike(){
        List<Menu> specificMenuList = simpleJPQlRepository.selectUsingLike();
        System.out.println(specificMenuList);
        assertNotNull(specificMenuList);
    }
}
