package com.ohgiraffers.section02.crud;

import org.hibernate.annotations.CollectionIdMutability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityManagerCRUDTests {

    private EntityManagerCRUD crud;
    // EntityManagerCRUD의 테스트 될 때마다 객체가 생성될 수 있도록(?)
    @BeforeEach
    void initManager(){
        this.crud = new EntityManagerCRUD();
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,2", "3,3"}) //전달할 파라미터를 여기에 적는다
    @DisplayName("Test read a menu by menucode")
    void testFindMenuByMenuCode(int menuCode, int expected) {
        //@Test given1
        //int menuCode = 1;
        //@ParameterizedTest, @CsvSource given2는 필요 없음. 작성 안 함

        //@Test when1
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        //@Test then1
        //System.out.println("foundMenu = " + foundMenu);

        //@ParameterizedTest, @CsvSource  then2 (여러가지 파라미터를 받아와서 여러 번 테스트하는 방법)
        assertEquals(expected, foundMenu.getMenuCode());
        System.out.println("foundMenu : " + foundMenu);
    }

    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "New Menu",
                        35000,
                        4,
                        "Y"
                )
        );
    }

    @DisplayName("Test Create New Menu")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus){

        //when
        Menu newMenu = new Menu(menuName, menuPrice,categoryCode,orderableStatus);
        Long count = crud.saveAndReturnAllCount(newMenu);

        //them
        assertEquals(21, count);
    }

    @DisplayName("Test Modify menu name")
    @ParameterizedTest
    @CsvSource("1, changed name") //한 번만 실행해줄 거라 {} 안 쓰고 () 안에 바로 넣어줌
    void testModifyMenuName(int menuCode, String menuName){
        //when
        Menu modifiedMenu = crud.modifyMenuName(menuCode,menuName);

        //then
        assertEquals(menuName, modifiedMenu.getMenuName());
    }

    @DisplayName("Test Delete menu")
    @ParameterizedTest
    @ValueSource(ints = {29})
    void testRemoveMenu(int menuCode){
        //when
        Long count = crud.removeAndReturnAllCount(menuCode);

        //them
        assertEquals(20, count);
    }
}
