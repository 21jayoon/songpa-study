package com.ohgiraffers.associationmapping.section02;

import com.ohgiraffers.associationmapping.section02.onetomany.Category;
import com.ohgiraffers.associationmapping.section02.onetomany.CategoryDTO;
import com.ohgiraffers.associationmapping.section02.onetomany.MenuDTO;
import com.ohgiraffers.associationmapping.section02.onetomany.OneToManyService;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OneToManyServiceTests {
    //field injection with @Autowired
    @Autowired
    private OneToManyService oneToManyService;

    @DisplayName("test searching 1:N relation")
    @Test
    void OneToManyFindTest(){
        //given
        int categoryCode = 4;

        //when
        Category category = oneToManyService.findCategory(categoryCode);

        //then
        assertNotNull(category);
    }

    private static Stream<Arguments> getMenuInfo() {
        return Stream.of(
                Arguments.of(321, "스파게티 돈가스", 30000, 321, "분식퓨전", "Y")
        );
    }
    @DisplayName("1:N 연관관계 객체 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getMenuInfo")
    void oneToManyInsertTest(
            int menuCode, String menuName, int menuPrice,
            int categoryCode, String categoryName, String orderableStatus
    ) {
        //given
        CategoryDTO categoryInfo = new CategoryDTO(
                categoryCode, categoryName, null, null
        );
        List<MenuDTO> menuList = new ArrayList<>();
        MenuDTO menuInfo = new MenuDTO(
                menuCode, menuName, menuPrice,categoryCode, orderableStatus
        );
        menuList.add(menuInfo);
        categoryInfo.setMenuList(menuList);
        //when
        //then
        Assertions.assertDoesNotThrow(
                () -> oneToManyService.registCategory(categoryInfo)
        );
    }
}
